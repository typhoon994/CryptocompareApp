package com.typhoon.cryptocompare.cryptocompareapp.data.networking

import com.typhoon.cryptocompare.cryptocompareapp.data.networking.model.CoinsResponse
import com.typhoon.cryptocompare.cryptocompareapp.data.networking.model.ComparisonResponse
import com.typhoon.cryptocompare.cryptocompareapp.data.networking.model.HistoryResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ICryptoApi {

    @GET("all/coinlist")
    fun getCoinList(): Call<CoinsResponse>

    @GET("price")
    fun getComparison(@Query("fsym") symbol: String,
                      @Query("tsyms") comparison: String)
            : Call<ComparisonResponse>

    @GET("histohour")
    fun getHourHistory(@Query("fsym") symbol: String,
                       @Query("tsym") toSymbol: String = "BTC",
                       @Query("limit") limit: Int) : Call<HistoryResponse>

    @GET("histominute")
    fun getMinuteHistory(@Query("fsym") symbol: String,
                       @Query("tsym") toSymbol: String = "BTC",
                       @Query("limit") limit: Int) : Call<HistoryResponse>

    @GET("histoday")
    fun getDayHistory(@Query("fsym") symbol: String,
                       @Query("tsym") toSymbol: String = "BTC",
                       @Query("limit") limit: Int) : Call<HistoryResponse>
}