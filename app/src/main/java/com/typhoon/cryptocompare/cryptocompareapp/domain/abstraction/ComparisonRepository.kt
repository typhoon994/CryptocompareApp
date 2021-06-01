package com.typhoon.cryptocompare.cryptocompareapp.domain.abstraction

import com.typhoon.cryptocompare.cryptocompareapp.domain.model.CoinComparison
import com.typhoon.cryptocompare.cryptocompareapp.domain.model.CoinHistory
import com.typhoon.cryptocompare.cryptocompareapp.domain.model.HistoryRate

interface ComparisonRepository {

    suspend fun compareSymbol(coinSymbol: String, compareWith: String): CoinComparison?
    suspend fun getCoinHistory(coinSymbol: String, compareWith: String, numOfHistoryItems: Int, rate: HistoryRate): CoinHistory?
}