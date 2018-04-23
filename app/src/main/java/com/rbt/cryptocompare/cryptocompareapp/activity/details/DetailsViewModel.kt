package com.rbt.cryptocompare.cryptocompareapp.activity.details

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.rbt.cryptocompare.cryptocompareapp.db.CoinDatabase
import com.rbt.cryptocompare.cryptocompareapp.db.CoinDbModel
import com.rbt.cryptocompare.cryptocompareapp.db.ComparisonDbModel
import com.rbt.cryptocompare.cryptocompareapp.networking.NetworkingHelper
import com.rbt.cryptocompare.cryptocompareapp.networking.model.CoinsResponse
import com.rbt.cryptocompare.cryptocompareapp.networking.model.ComparisonResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsViewModel : ViewModel(), IMoreDetailsViewModel {

    private val comparisonData = MutableLiveData<CoinComparison>()
    private var db: CoinDatabase? = null

    override fun setDBInstance(db: CoinDatabase) { this.db = db }

    private fun onDataFetched(symbol: String, response: ComparisonResponse?) {
        response?.let {
            Thread(Runnable {
                val dbModel = ComparisonDbModel.getInstanceFromComparisonResponse(symbol, it)
                db?.coinDao()?.insertComparison(dbModel)
            }).start()

            comparisonData.postValue(CoinComparison.getInstanceFromNetworkModel(it))
            return
        }

        Thread(Runnable {
            val model = db?.coinDao()?.getComparison(symbol)
            val comparison = if(model != null) CoinComparison.getInstanceFromDBModel(model) else null

            comparisonData.postValue(comparison)
        }).start()
    }

    override fun getComparisonResults(symbol: String): LiveData<CoinComparison?> {

        val networkingInstance = NetworkingHelper.getInstance()
        networkingInstance.getComparison(symbol).enqueue(object: Callback<ComparisonResponse> {
            override fun onFailure(call: Call<ComparisonResponse>?, t: Throwable?) {
                onDataFetched(symbol, null)
            }

            override fun onResponse(call: Call<ComparisonResponse>?, response: Response<ComparisonResponse>?) {
                onDataFetched(symbol, response?.body())
            }
        })

        return comparisonData;
    }
}