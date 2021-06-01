package com.typhoon.cryptocompare.cryptocompareapp.domain.usecase

import com.typhoon.cryptocompare.cryptocompareapp.domain.abstraction.ComparisonRepository
import com.typhoon.cryptocompare.cryptocompareapp.domain.model.CoinComparison
import com.typhoon.cryptocompare.cryptocompareapp.domain.model.CoinHistory
import com.typhoon.cryptocompare.cryptocompareapp.domain.model.HistoryInterval
import com.typhoon.cryptocompare.cryptocompareapp.domain.model.HistoryRate
import javax.inject.Inject

class CoinComparisonUseCase @Inject constructor(
    private val comparisonRepository: ComparisonRepository
) {
    companion object {
        private const val COMPARISON_CRITERIA = "BTC,ETH,EVN,DOGE,ZEC,USD,EUR"
    }

    suspend fun getComparison(coinSymbol: String): CoinComparison? {
        return comparisonRepository.compareSymbol(coinSymbol, COMPARISON_CRITERIA)
    }

    suspend fun getComparisonForInterval(
        coinSymbol: String,
        interval: HistoryInterval,
        rate: HistoryRate
    ): CoinHistory? {
        val numberOfItems = interval.timeInMinutes / rate.timeInMinutes
        val compareSymbol = if (coinSymbol == "BTC") "EUR" else "BTC"
        return comparisonRepository.getCoinHistory(coinSymbol, compareSymbol, numberOfItems, rate)
    }
}