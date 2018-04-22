package com.rbt.cryptocompare.cryptocompareapp.splash

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.rbt.cryptocompare.cryptocompareapp.model.CoinsResponse
import com.rbt.cryptocompare.cryptocompareapp.networking.NetworkingHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashViewModel : ViewModel(), ISplashViewModel {

    private val errorData = MutableLiveData<String?>()
    private val networkingHelper = NetworkingHelper.getInstance()

    override fun cacheMainData(): LiveData<String?> {

        networkingHelper.getCoinList().enqueue(object : Callback<CoinsResponse> {
            override fun onResponse(call: Call<CoinsResponse>?, response: Response<CoinsResponse>?) {
                response?.let {
                    if (!it.isSuccessful) return@let

                    errorData.postValue(null)
                    return
                }

                errorData.postValue("Unknown error has occurred, app may wont work as expected")
            }

            override fun onFailure(call: Call<CoinsResponse>?, t: Throwable?) {
                errorData.postValue(t?.localizedMessage ?: "Unknown error has occurred, app may wont work as expected")
            }
        })

        return errorData
    }
}