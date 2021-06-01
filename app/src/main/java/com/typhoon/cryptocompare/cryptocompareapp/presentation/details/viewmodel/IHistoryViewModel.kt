package com.typhoon.cryptocompare.cryptocompareapp.presentation.details.viewmodel

import androidx.lifecycle.LiveData
import com.typhoon.cryptocompare.cryptocompareapp.domain.model.CoinHistory

interface IHistoryViewModel {
    val historyData: LiveData<CoinHistory>

    fun onComparisonCriteriaChanged(symbol: String, interval: String, rate: String)
}