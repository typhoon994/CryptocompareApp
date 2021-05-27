package com.rbt.cryptocompare.cryptocompareapp.activity.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rbt.cryptocompare.cryptocompareapp.db.CoinDatabase
import com.rbt.cryptocompare.cryptocompareapp.db.CoinDbModel
import com.rbt.cryptocompare.cryptocompareapp.networking.NetworkingHelper
import com.rbt.cryptocompare.cryptocompareapp.networking.model.CoinsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SplashViewModel : ViewModel(), ISplashViewModel {

    private var db: CoinDatabase? = null
    private val errorData = MutableLiveData<String?>()
    private val networkingHelper = NetworkingHelper.getInstance()

    override fun setDBInstance(db: CoinDatabase) {
        this.db = db
    }

    override fun cacheMainData(): LiveData<String?> {
        networkingHelper.getCoinList().enqueue(object : Callback<CoinsResponse> {
            override fun onResponse(call: Call<CoinsResponse>?, response: Response<CoinsResponse>?) {
                response?.let {
                    if (!it.isSuccessful || it.body() == null) return@let

                    val coinsList = (it.body() as CoinsResponse).Data.values
                    val baseUrl = response.body()?.BaseImageUrl
                    val dbList = coinsList.map {
                        CoinDbModel.instanceFromNetworkingModel(it, baseUrl!!)
                    }.toTypedArray()

                    viewModelScope.launch(Dispatchers.IO) {
                        db?.coinDao()?.insertAll(dbList)
                        withContext(Dispatchers.Main) { errorData.postValue(null) }
                    }

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