package com.rbt.cryptocompare.cryptocompareapp.main

import android.arch.lifecycle.LiveData

interface IMainViewModel {

    fun getMainDataObservable() : LiveData<MainDataModel>
    fun getMainData()
}