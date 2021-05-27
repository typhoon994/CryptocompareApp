package com.rbt.cryptocompare.cryptocompareapp.activity.details.viewmodel

import androidx.lifecycle.LiveData
import com.rbt.cryptocompare.cryptocompareapp.activity.details.model.CoinComparison
import com.rbt.cryptocompare.cryptocompareapp.db.CoinDatabase

interface IMoreDetailsViewModel {

    fun getComparisonResults(symbol: String) : LiveData<CoinComparison?>
    fun setDBInstance(db: CoinDatabase)
}