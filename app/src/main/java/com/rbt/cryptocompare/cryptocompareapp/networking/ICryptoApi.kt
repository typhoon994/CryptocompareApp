package com.rbt.cryptocompare.cryptocompareapp.networking

import com.rbt.cryptocompare.cryptocompareapp.networking.model.CoinsResponse
import com.rbt.cryptocompare.cryptocompareapp.networking.model.ComparisonResponse
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
}