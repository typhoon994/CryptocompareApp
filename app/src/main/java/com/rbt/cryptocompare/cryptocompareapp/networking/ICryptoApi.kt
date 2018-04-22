package com.rbt.cryptocompare.cryptocompareapp.networking

import com.rbt.cryptocompare.cryptocompareapp.networking.model.CoinsResponse
import retrofit2.Call
import retrofit2.http.GET

interface ICryptoApi {

    @GET("all/coinlist") fun getCoinList() : Call<CoinsResponse>
}