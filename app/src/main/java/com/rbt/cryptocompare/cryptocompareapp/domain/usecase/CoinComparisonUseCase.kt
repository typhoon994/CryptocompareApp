package com.rbt.cryptocompare.cryptocompareapp.domain.usecase

import com.rbt.cryptocompare.cryptocompareapp.db.CoinDatabase
import com.rbt.cryptocompare.cryptocompareapp.db.ComparisonDbModel
import com.rbt.cryptocompare.cryptocompareapp.domain.model.CoinComparison
import com.rbt.cryptocompare.cryptocompareapp.domain.model.HistoryInterval
import com.rbt.cryptocompare.cryptocompareapp.domain.model.CoinHistory
import com.rbt.cryptocompare.cryptocompareapp.domain.model.HistoryRate
import com.rbt.cryptocompare.cryptocompareapp.networking.ICryptoApi
import com.rbt.cryptocompare.cryptocompareapp.networking.NetworkingHelper

class CoinComparisonUseCase(
    private val networkingHelper: ICryptoApi = NetworkingHelper.getInstance(),
    private val database: CoinDatabase?
) {
    suspend fun getComparison(coinSymbol: String): CoinComparison? {
        val response = networkingHelper.getComparison(coinSymbol).execute()
        val responseBody = response.body()
        return if (response.isSuccessful && responseBody != null) {
            val dbModel =
                ComparisonDbModel.getInstanceFromComparisonResponse(coinSymbol, responseBody)
            database?.coinDao()?.insertComparison(dbModel)
            CoinComparison.getInstanceFromDBModel(dbModel)
        } else {
            val model = database?.coinDao()?.getComparison(coinSymbol)
            model?.let { CoinComparison.getInstanceFromDBModel(model) }
        }
    }

    suspend fun getComparisonForInterval(
        coinSymbol: String,
        interval: HistoryInterval,
        rate: HistoryRate
    ): CoinHistory? {
        val numberOfItems = interval.timeInMinutes / rate.timeInMinutes
        val compareSymbol = if (coinSymbol == "BTC") "EUR" else "BTC"

        val response = when (rate) {
            HistoryRate.Hour -> networkingHelper.getHourHistory(
                coinSymbol,
                compareSymbol,
                numberOfItems
            ).execute()
            HistoryRate.Minute -> networkingHelper.getMinuteHistory(
                coinSymbol,
                compareSymbol,
                numberOfItems
            ).execute()
            else -> networkingHelper.getDayHistory(coinSymbol, compareSymbol, numberOfItems)
                .execute()
        }

        return if (response.isSuccessful) response.body()?.let {
            CoinHistory.getInstanceFromNetworkModel(it, compareSymbol)
        } else null
    }
}