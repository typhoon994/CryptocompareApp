package com.rbt.cryptocompare.cryptocompareapp.presentation.details.viewmodel

import androidx.lifecycle.LiveData
import com.rbt.cryptocompare.cryptocompareapp.data.db.CoinDatabase
import com.rbt.cryptocompare.cryptocompareapp.domain.model.CoinComparison

interface IMoreDetailsViewModel {
    val comparisonDataObservable: LiveData<CoinComparison>

    fun onViewShown(symbol: String)
    fun setDBInstance(db: CoinDatabase)
}