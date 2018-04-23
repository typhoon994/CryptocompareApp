package com.rbt.cryptocompare.cryptocompareapp.networking

import com.rbt.cryptocompare.cryptocompareapp.networking.model.CoinsResponse
import com.rbt.cryptocompare.cryptocompareapp.networking.model.ComparisonResponse
import com.rbt.cryptocompare.cryptocompareapp.networking.model.HistoryResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ICryptoApi {

    @GET("all/coinlist")
    fun getCoinList(): Call<CoinsResponse>

    @GET("price")
    fun getComparison(@Query("fsym") symbol: String,
                      @Query("tsyms") comparison: String = "BTC,ETH,EVN,DOGE,ZEC,USD,EUR")
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