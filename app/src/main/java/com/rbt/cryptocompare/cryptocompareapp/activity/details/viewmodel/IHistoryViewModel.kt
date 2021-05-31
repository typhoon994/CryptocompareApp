package com.rbt.cryptocompare.cryptocompareapp.activity.details.viewmodel

import androidx.lifecycle.LiveData
import com.rbt.cryptocompare.cryptocompareapp.domain.model.CoinHistory
import com.rbt.cryptocompare.cryptocompareapp.db.CoinDatabase
import com.rbt.cryptocompare.cryptocompareapp.domain.model.CoinComparison

interface IHistoryViewModel {
    val historyData: LiveData<CoinHistory>

    fun onComparisonCriteriaChanged(symbol: String, interval: String, rate: String)
    fun setDBInstance(db: CoinDatabase)
}