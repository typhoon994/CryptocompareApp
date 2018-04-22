package com.rbt.cryptocompare.cryptocompareapp.splash

import android.arch.lifecycle.LiveData

interface ISplashViewModel {

    fun cacheMainData() : LiveData<String?>
}