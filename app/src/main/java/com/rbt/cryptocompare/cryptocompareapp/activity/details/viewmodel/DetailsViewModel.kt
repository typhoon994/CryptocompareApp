package com.rbt.cryptocompare.cryptocompareapp.activity.details.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rbt.cryptocompare.cryptocompareapp.activity.details.model.CoinComparison
import com.rbt.cryptocompare.cryptocompareapp.activity.details.model.HistoryModel
import com.rbt.cryptocompare.cryptocompareapp.db.CoinDatabase
import com.rbt.cryptocompare.cryptocompareapp.db.ComparisonDbModel
import com.rbt.cryptocompare.cryptocompareapp.networking.NetworkingHelper
import com.rbt.cryptocompare.cryptocompareapp.networking.model.ComparisonResponse
import com.rbt.cryptocompare.cryptocompareapp.networking.model.HistoryResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsViewModel : ViewModel(), IMoreDetailsViewModel, IHistoryViewModel {

    private val comparisonData = MutableLiveData<CoinComparison>()
    private val historyData = MutableLiveData<HistoryModel>()
    private var db: CoinDatabase? = null
    private var compareSymbol: String = ""

    private val INTERVAL_VALUES: HashMap<String, Int> = hashMapOf("Minute" to 1,
            "Hour" to 60,
            "Three Hours" to 3*60,
            "Day" to 24*60,
            "Three Days" to 3*24*60,
            "Week" to 7*24*60,
            "Two Weeks" to 2*7*24*60,
            "Month" to 30*24*60)

    private val callback = object : Callback<HistoryResponse> {
        override fun onFailure(call: Call<HistoryResponse>?, t: Throwable?) {

        }

        override fun onResponse(call: Call<HistoryResponse>?, response: Response<HistoryResponse>?) {
            if (response == null || response.body() == null) return

            historyData.postValue(HistoryModel.getInstanceFromNetworkModel(response.body() as HistoryResponse,
                    compareSymbol))
        }
    }

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

    override fun getComparisonResults(symbol: String, interval: String, rate: String): LiveData<HistoryModel> {
        val rateValue = INTERVAL_VALUES[rate] ?: 24*60
        val intervalValue = INTERVAL_VALUES[interval] ?: 24*60

        val numberOfItems = rateValue/intervalValue
        compareSymbol = if (symbol == "BTC") "EUR" else "BTC"

        when (interval) {
            "Hour" -> NetworkingHelper.getInstance().getHourHistory(symbol, compareSymbol, numberOfItems).enqueue(callback)
            "Minute" -> NetworkingHelper.getInstance().getMinuteHistory(symbol, compareSymbol, numberOfItems).enqueue(callback)
            else -> NetworkingHelper.getInstance().getDayHistory(symbol, compareSymbol, numberOfItems).enqueue(callback)
        }

        return historyData
    }
}