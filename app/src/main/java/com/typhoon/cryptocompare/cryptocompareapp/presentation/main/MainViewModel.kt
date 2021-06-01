package com.typhoon.cryptocompare.cryptocompareapp.presentation.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.typhoon.cryptocompare.cryptocompareapp.domain.usecase.CoinListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val coinListUseCase: CoinListUseCase) : ViewModel(),
    IMainViewModel {

    override val mainDataObservable = MutableLiveData<MainDataModel>()

    override fun onViewShown() {
        viewModelScope.launch(Dispatchers.IO) {
            val coinList = coinListUseCase.getAll()
            withContext(Dispatchers.Main) { mainDataObservable.postValue(MainDataModel(coinList)) }
        }
    }
}