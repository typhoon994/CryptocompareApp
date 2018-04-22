package com.rbt.cryptocompare.cryptocompareapp.activity.main

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.rbt.cryptocompare.cryptocompareapp.db.CoinDatabase

class MainViewModel : ViewModel(), IMainViewModel {

    private val mainModel = MutableLiveData<MainDataModel>()
    private var db: CoinDatabase? = null

    override fun getMainDataObservable() = mainModel

    override fun setDbInstance(db: CoinDatabase) { this.db = db }

    override fun getMainData() {
        val dbModel = db?.coinDao()?.getAll()
        val mainModelList = dbModel?.map { MainDataModel.CoinItem(it.Name, it.Symbol, it.ImageUrl) }?.toTypedArray() ?: emptyArray()

        mainModel.postValue(MainDataModel(mainModelList))
    }
}