package com.typhoon.cryptocompare.cryptocompareapp.presentation.details.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.typhoon.cryptocompare.cryptocompareapp.domain.model.CoinComparison
import com.typhoon.cryptocompare.cryptocompareapp.domain.model.CoinHistory
import com.typhoon.cryptocompare.cryptocompareapp.domain.model.HistoryInterval
import com.typhoon.cryptocompare.cryptocompareapp.domain.model.HistoryRate
import com.typhoon.cryptocompare.cryptocompareapp.domain.usecase.CoinComparisonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val coinComparisonUseCase: CoinComparisonUseCase) :
    ViewModel(), IMoreDetailsViewModel, IHistoryViewModel {

    override val comparisonDataObservable = MutableLiveData<CoinComparison>()
    override val historyData = MutableLiveData<CoinHistory>()

    override fun onViewShown(symbol: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val model = coinComparisonUseCase.getComparison(symbol)
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
            val model = coinComparisonUseCase.getComparisonForInterval(
                symbol,
                historyInterval,
                historyRate
            )
            withContext(Dispatchers.Main) { historyData.value = model }
        }
    }
}