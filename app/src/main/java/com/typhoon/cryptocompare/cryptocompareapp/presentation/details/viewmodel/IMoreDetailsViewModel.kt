package com.typhoon.cryptocompare.cryptocompareapp.presentation.details.viewmodel

import androidx.lifecycle.LiveData
import com.typhoon.cryptocompare.cryptocompareapp.domain.model.CoinComparison

interface IMoreDetailsViewModel {
    val comparisonDataObservable: LiveData<CoinComparison>

    fun onViewShown(symbol: String)
}