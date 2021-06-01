package com.typhoon.cryptocompare.cryptocompareapp.presentation.splash

import androidx.lifecycle.LiveData

interface ISplashViewModel {
    val dismissSplashObservable: LiveData<Void?>
    fun onViewShown()
}