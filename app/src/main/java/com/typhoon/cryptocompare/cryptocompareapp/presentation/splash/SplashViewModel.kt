package com.typhoon.cryptocompare.cryptocompareapp.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.typhoon.cryptocompare.cryptocompareapp.domain.usecase.CoinListUseCase
import com.typhoon.cryptocompare.cryptocompareapp.presentation.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor(private val coinListUseCase: CoinListUseCase) :
    ViewModel(),
    ISplashViewModel {

    override val dismissSplashObservable = SingleLiveEvent<Void?>()

    override fun onViewShown() {
        viewModelScope.launch(Dispatchers.IO) {
            coinListUseCase.sync()
            withContext(Dispatchers.Main) { dismissSplashObservable.call() }
        }
    }
}