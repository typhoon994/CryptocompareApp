package com.rbt.cryptocompare.cryptocompareapp.data.repository

import com.rbt.cryptocompare.cryptocompareapp.data.db.CoinDatabase
import com.rbt.cryptocompare.cryptocompareapp.data.db.ComparisonDbModel
import com.rbt.cryptocompare.cryptocompareapp.data.networking.ICryptoApi
import com.rbt.cryptocompare.cryptocompareapp.data.networking.NetworkingHelper
import com.rbt.cryptocompare.cryptocompareapp.data.networking.model.ComparisonResponse
import com.rbt.cryptocompare.cryptocompareapp.domain.abstraction.ComparisonRepository
import com.rbt.cryptocompare.cryptocompareapp.domain.model.CoinComparison
import com.rbt.cryptocompare.cryptocompareapp.domain.model.CoinHistory
import com.rbt.cryptocompare.cryptocompareapp.domain.model.HistoryRate
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resumeWithException

class ComparisonRepositoryImpl(
    private val networkingHelper: ICryptoApi = NetworkingHelper.getInstance(),
    private val database: CoinDatabase?
) : ComparisonRepository {

    override suspend fun compareSymbol(coinSymbol: String, compareWith: String): CoinComparison? {
        return try {
            val coinComparison = getComparison(coinSymbol, compareWith)
            val dbModel =
                ComparisonDbModel.getInstanceFromComparisonResponse(coinSymbol, coinComparison)
            database?.coinDao()?.insertComparison(dbModel)
            CoinComparison.getInstanceFromDBModel(dbModel)
        } catch (exception: Exception) {
            val model = database?.coinDao()?.getComparison(coinSymbol)
            model?.let { CoinComparison.getInstanceFromDBModel(model) }
        }
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun getCoinHistory(
        coinSymbol: String,
        compareWith: String,
        numOfHistoryItems: Int,
        rate: HistoryRate
    ): CoinHistory? {
        val response = when (rate) {
            HistoryRate.Hour -> networkingHelper.getHourHistory(
                coinSymbol,
                compareWith,
                numOfHistoryItems
            ).execute()
            HistoryRate.Minute -> networkingHelper.getMinuteHistory(
                coinSymbol,
                compareWith,
                numOfHistoryItems
            ).execute()
            else -> networkingHelper.getDayHistory(
                coinSymbol,
                compareWith,
                numOfHistoryItems
            ).execute()
        }

        return if (response.isSuccessful) response.body()?.let {
            CoinHistory.getInstanceFromNetworkModel(it, compareWith)
        } else null
    }

    private suspend fun getComparison(coinSymbol: String, compareWith: String): ComparisonResponse {
        return suspendCancellableCoroutine { continuation ->
            networkingHelper.getComparison(coinSymbol, compareWith)
                .enqueue(object : Callback<ComparisonResponse> {
                    override fun onResponse(
                        call: Call<ComparisonResponse>,
                        response: Response<ComparisonResponse>
                    ) {
                        val coinsResponse = response.body()
                        if (response.isSuccessful && coinsResponse != null) {
                            continuation.resumeWith(Result.success(coinsResponse))
                        } else {
                            continuation.resumeWithException(Throwable())
                        }
                    }

                    override fun onFailure(call: Call<ComparisonResponse>, t: Throwable) {
                        continuation.resumeWithException(t)
                    }
                })
        }
    }
}