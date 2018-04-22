package com.rbt.cryptocompare.cryptocompareapp.main

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class MainViewModel : ViewModel(), IMainViewModel {

    private val mainModel = MutableLiveData<MainDataModel>()

    override fun getMainDataObservable() = mainModel

    override fun getMainData() {
        // Mocked ATM
        val list = Array<MainDataModel.CoinItem>(3, { i -> MainDataModel.CoinItem("Name" + i,
                "Symbol" + i, "Url")})
        val model = MainDataModel(list)

        mainModel.postValue(model)
    }
}