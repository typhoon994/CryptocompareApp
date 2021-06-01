package com.rbt.cryptocompare.cryptocompareapp.presentation.main

import androidx.lifecycle.LiveData
import com.rbt.cryptocompare.cryptocompareapp.data.db.CoinDatabase

interface IMainViewModel {
    val mainDataObservable : LiveData<MainDataModel>

    fun onViewShown()
    fun setDbInstance(db: CoinDatabase)
}