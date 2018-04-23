package com.rbt.cryptocompare.cryptocompareapp.activity.details

import android.arch.lifecycle.LiveData
import com.rbt.cryptocompare.cryptocompareapp.db.CoinDatabase

interface IMoreDetailsViewModel {

    fun getComparisonResults(symbol: String) : LiveData<CoinComparison?>
    fun setDBInstance(db: CoinDatabase)
}