package com.typhoon.cryptocompare.cryptocompareapp.data.repository

import com.typhoon.cryptocompare.cryptocompareapp.data.db.CoinDatabase
import com.typhoon.cryptocompare.cryptocompareapp.data.db.CoinDbModel
import com.typhoon.cryptocompare.cryptocompareapp.data.networking.ICryptoApi
import com.typhoon.cryptocompare.cryptocompareapp.data.networking.model.CoinsResponse
import com.typhoon.cryptocompare.cryptocompareapp.domain.abstraction.CoinRepository
import com.typhoon.cryptocompare.cryptocompareapp.domain.model.CoinItem
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject
import kotlin.coroutines.resumeWithException

class CoinRepositoryImpl @Inject constructor(
    private val networkingHelper: ICryptoApi,
    private val database: CoinDatabase
) : CoinRepository {

    override suspend fun cacheData() {
        try {
            val result = getCoinsFromNetwork()
            val coinsList = result.Data.values
            val baseUrl = result.BaseImageUrl
            val dbList = coinsList.map { CoinDbModel.instanceFromNetworkingModel(it, baseUrl) }
            database.coinDao().insertAll(dbList)
        } catch (exception: IOException) {
            // Add proper error handling
        }
    }

    override suspend fun getAll(): List<CoinItem> {
        val dbModel = database.coinDao().getAll()
        val mainModelList = dbModel.map {
            CoinItem.getInstanceFromDbModel(it)
        }
        return mainModelList ?: emptyList()
    }

    private suspend fun getCoinsFromNetwork(): CoinsResponse {
        return suspendCancellableCoroutine { continuation ->
            networkingHelper.getCoinList().enqueue(object : Callback<CoinsResponse> {
                override fun onResponse(
                    call: Call<CoinsResponse>,
                    response: Response<CoinsResponse>
                ) {
                    val coinsResponse = response.body()
                    if (response.isSuccessful && coinsResponse != null) {
                        continuation.resumeWith(Result.success(coinsResponse))
                    } else {
                        continuation.resumeWithException(Throwable())
                    }
                }

                override fun onFailure(call: Call<CoinsResponse>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }
}