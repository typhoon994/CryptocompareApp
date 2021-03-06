package com.rbt.cryptocompare.cryptocompareapp.activity.main

import android.arch.lifecycle.LiveData
import com.rbt.cryptocompare.cryptocompareapp.db.CoinDatabase

interface IMainViewModel {

    fun getMainDataObservable() : LiveData<MainDataModel>
    fun getMainData()
    fun setDbInstance(db: CoinDatabase)
}