package com.rbt.cryptocompare.cryptocompareapp.presentation.details.viewmodel

import androidx.lifecycle.LiveData
import com.rbt.cryptocompare.cryptocompareapp.data.db.CoinDatabase
import com.rbt.cryptocompare.cryptocompareapp.domain.model.CoinHistory

interface IHistoryViewModel {
    val historyData: LiveData<CoinHistory>

    fun onComparisonCriteriaChanged(symbol: String, interval: String, rate: String)
    fun setDBInstance(db: CoinDatabase)
}