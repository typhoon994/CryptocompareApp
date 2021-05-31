package com.rbt.cryptocompare.cryptocompareapp.activity.splash

import androidx.lifecycle.LiveData
import com.rbt.cryptocompare.cryptocompareapp.db.CoinDatabase

interface ISplashViewModel {

    val dismissSplashObservable: LiveData<Void?>

    fun onViewShown()
    fun setDBInstance(db: CoinDatabase)
}