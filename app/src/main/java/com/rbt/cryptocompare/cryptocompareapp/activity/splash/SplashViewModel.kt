package com.rbt.cryptocompare.cryptocompareapp.activity.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rbt.cryptocompare.cryptocompareapp.activity.util.SingleLiveEvent
import com.rbt.cryptocompare.cryptocompareapp.db.CoinDatabase
import com.rbt.cryptocompare.cryptocompareapp.domain.usecase.CoinListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SplashViewModel : ViewModel(), ISplashViewModel {

    private var db: CoinDatabase? = null

    override val dismissSplashObservable = SingleLiveEvent<Void?>()

    override fun setDBInstance(db: CoinDatabase) {
        this.db = db
    }

    override fun onViewShown() {
        viewModelScope.launch(Dispatchers.IO) {
            CoinListUseCase(database = db).sync()
            withContext(Dispatchers.Main) { dismissSplashObservable.call() }
        }
    }
}