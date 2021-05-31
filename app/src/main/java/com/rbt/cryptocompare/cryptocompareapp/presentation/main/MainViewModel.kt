package com.rbt.cryptocompare.cryptocompareapp.presentation.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rbt.cryptocompare.cryptocompareapp.db.CoinDatabase
import com.rbt.cryptocompare.cryptocompareapp.domain.usecase.CoinListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel(), IMainViewModel {

    override val mainDataObservable = MutableLiveData<MainDataModel>()

    private var db: CoinDatabase? = null

    override fun setDbInstance(db: CoinDatabase) { this.db = db }

    override fun onViewShown() {
        viewModelScope.launch(Dispatchers.IO) {
            val coinList = CoinListUseCase(database = db).getAll()
            withContext(Dispatchers.Main) { mainDataObservable.postValue(MainDataModel(coinList)) }
        }
    }
}