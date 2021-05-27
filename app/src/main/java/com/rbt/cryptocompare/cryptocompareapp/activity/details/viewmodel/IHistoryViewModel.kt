package com.rbt.cryptocompare.cryptocompareapp.activity.details.viewmodel

import androidx.lifecycle.LiveData
import com.rbt.cryptocompare.cryptocompareapp.activity.details.model.HistoryModel
import com.rbt.cryptocompare.cryptocompareapp.db.CoinDatabase

interface IHistoryViewModel {

    fun getComparisonResults(symbol: String, interval: String, rate: String) : LiveData<HistoryModel>
    fun setDBInstance(db: CoinDatabase)
}