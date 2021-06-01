package com.rbt.cryptocompare.cryptocompareapp.domain.usecase

import com.rbt.cryptocompare.cryptocompareapp.data.db.CoinDatabase
import com.rbt.cryptocompare.cryptocompareapp.data.repository.ComparisonRepositoryImpl
import com.rbt.cryptocompare.cryptocompareapp.domain.model.CoinComparison
import com.rbt.cryptocompare.cryptocompareapp.domain.model.CoinHistory
import com.rbt.cryptocompare.cryptocompareapp.domain.model.HistoryInterval
import com.rbt.cryptocompare.cryptocompareapp.domain.model.HistoryRate

class CoinComparisonUseCase(private val database: CoinDatabase?) {
    companion object {
        private const val COMPARISON_CRITERIA = "BTC,ETH,EVN,DOGE,ZEC,USD,EUR"
    }

    suspend fun getComparison(coinSymbol: String): CoinComparison? {
        return ComparisonRepositoryImpl(database = database).compareSymbol(
            coinSymbol,
            COMPARISON_CRITERIA
        )
    }

    suspend fun getComparisonForInterval(
        coinSymbol: String,
        interval: HistoryInterval,
        rate: HistoryRate
    ): CoinHistory? {
        val numberOfItems = interval.timeInMinutes / rate.timeInMinutes
        val compareSymbol = if (coinSymbol == "BTC") "EUR" else "BTC"
        return ComparisonRepositoryImpl(database = database).getCoinHistory(
            coinSymbol,
            compareSymbol,
            numberOfItems,
            rate
        )
    }
}