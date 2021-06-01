package com.rbt.cryptocompare.cryptocompareapp.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rbt.cryptocompare.cryptocompareapp.data.db.CoinDatabase
import com.rbt.cryptocompare.cryptocompareapp.domain.usecase.CoinListUseCase
import com.rbt.cryptocompare.cryptocompareapp.presentation.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SplashViewModel : ViewModel(), ISplashViewModel {

    private var db: CoinDatabase? = null

    override val dismissSplashObservable = SingleLiveEvent<Void?>()

    override fun setDBInstance(db: CoinDatabase) {
        this.db = db
    }

    override fun onViewShown() {
        viewModelScope.launch(Dispatchers.IO) {
            CoinListUseCase(database = db).sync()
            withContext(Dispatchers.Main) { dismissSplashObservable.call() }
        }
    }
}