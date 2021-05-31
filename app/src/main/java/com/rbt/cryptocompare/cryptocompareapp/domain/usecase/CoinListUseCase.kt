package com.rbt.cryptocompare.cryptocompareapp.domain.usecase

import com.rbt.cryptocompare.cryptocompareapp.db.CoinDatabase
import com.rbt.cryptocompare.cryptocompareapp.db.CoinDbModel
import com.rbt.cryptocompare.cryptocompareapp.domain.model.CoinItem
import com.rbt.cryptocompare.cryptocompareapp.networking.ICryptoApi
import com.rbt.cryptocompare.cryptocompareapp.networking.NetworkingHelper
import com.rbt.cryptocompare.cryptocompareapp.networking.model.CoinsResponse
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import kotlin.coroutines.resumeWithException

class CoinListUseCase(
    private val networkingHelper: ICryptoApi = NetworkingHelper.getInstance(),
    private val database: CoinDatabase?
) {

    suspend fun sync() {
        try {
            val response = networkingHelper.getCoinList().execute()
            if (!response.isSuccessful || response.body() == null) return

            val coinsList = (response.body() as CoinsResponse).Data.values
            val baseUrl = response.body()?.BaseImageUrl
            val dbList = coinsList.map {
                CoinDbModel.instanceFromNetworkingModel(it, baseUrl!!)
            }.toTypedArray()

            database?.coinDao()?.insertAll(dbList)
        } catch (exception: IOException) {
            // Ignore for now
        }
    }

    suspend fun getAll(): List<CoinItem> {
        val dbModel = database?.coinDao()?.getAll()
        val mainModelList = dbModel?.map {
            CoinItem.getInstanceFromDbModel(it)
        }
        return mainModelList ?: emptyList()
    }

    // TODO Move to repository
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