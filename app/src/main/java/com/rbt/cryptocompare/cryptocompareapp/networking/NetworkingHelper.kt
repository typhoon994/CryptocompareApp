package com.rbt.cryptocompare.cryptocompareapp.networking

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


abstract class NetworkingHelper {

    companion object {
        private var helper : ICryptoApi = NetworkingHelper.createInstance()

        private fun createInstance() : ICryptoApi {
            val retrofit = Retrofit.Builder()
                    .baseUrl("https://min-api.cryptocompare.com/data/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            return retrofit.create<ICryptoApi>(ICryptoApi::class.java)
        }

        fun getInstance() = helper
    }
}