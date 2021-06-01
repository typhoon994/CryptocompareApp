package com.rbt.cryptocompare.cryptocompareapp.presentation.splash

import androidx.lifecycle.LiveData
import com.rbt.cryptocompare.cryptocompareapp.data.db.CoinDatabase

interface ISplashViewModel {

    val dismissSplashObservable: LiveData<Void?>

    fun onViewShown()
    fun setDBInstance(db: CoinDatabase)
}