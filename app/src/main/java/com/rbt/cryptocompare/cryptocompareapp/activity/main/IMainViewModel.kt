package com.rbt.cryptocompare.cryptocompareapp.activity.main

import androidx.lifecycle.LiveData
import com.rbt.cryptocompare.cryptocompareapp.db.CoinDatabase

interface IMainViewModel {
    val mainDataObservable : LiveData<MainDataModel>

    fun onViewShown()
    fun setDbInstance(db: CoinDatabase)
}