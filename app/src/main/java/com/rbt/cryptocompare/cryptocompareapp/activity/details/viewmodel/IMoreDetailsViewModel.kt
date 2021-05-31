package com.rbt.cryptocompare.cryptocompareapp.activity.details.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rbt.cryptocompare.cryptocompareapp.domain.model.CoinComparison
import com.rbt.cryptocompare.cryptocompareapp.db.CoinDatabase

interface IMoreDetailsViewModel {
    val comparisonDataObservable: LiveData<CoinComparison>

    fun onViewShown(symbol: String)
    fun setDBInstance(db: CoinDatabase)
}