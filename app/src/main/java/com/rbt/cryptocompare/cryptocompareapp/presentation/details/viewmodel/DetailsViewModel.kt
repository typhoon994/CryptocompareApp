package com.rbt.cryptocompare.cryptocompareapp.presentation.details.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rbt.cryptocompare.cryptocompareapp.data.db.CoinDatabase
import com.rbt.cryptocompare.cryptocompareapp.domain.model.CoinComparison
import com.rbt.cryptocompare.cryptocompareapp.domain.model.CoinHistory
import com.rbt.cryptocompare.cryptocompareapp.domain.model.HistoryInterval
import com.rbt.cryptocompare.cryptocompareapp.domain.model.HistoryRate
import com.rbt.cryptocompare.cryptocompareapp.domain.usecase.CoinComparisonUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailsViewModel : ViewModel(), IMoreDetailsViewModel, IHistoryViewModel {

    override val comparisonDataObservable = MutableLiveData<CoinComparison>()
    override val historyData = MutableLiveData<CoinHistory>()

    private var db: CoinDatabase? = null

    override fun setDBInstance(db: CoinDatabase) {
        this.db = db
    }

    override fun onViewShown(symbol: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val model = CoinComparisonUseCase(database = db).getComparison(symbol)
            withContext(Dispatchers.Main) { comparisonDataObservable.value = model }
        }
    }

    override fun onComparisonCriteriaChanged(
        symbol: String,
        interval: String,
        rate: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val historyInterval = HistoryInterval.getFromDisplayText(interval)
            val historyRate = HistoryRate.getFromDisplayText(rate)
            val model = CoinComparisonUseCase(database = db).getComparisonForInterval(
                symbol,
                historyInterval,
                historyRate
            )
            withContext(Dispatchers.Main) { historyData.value = model }
        }
    }
}