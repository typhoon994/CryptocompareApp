package com.rbt.cryptocompare.cryptocompareapp.activity.splash

import androidx.lifecycle.LiveData
import com.rbt.cryptocompare.cryptocompareapp.db.CoinDatabase

interface ISplashViewModel {

    fun cacheMainData() : LiveData<String?>
    fun setDBInstance(db: CoinDatabase)
}