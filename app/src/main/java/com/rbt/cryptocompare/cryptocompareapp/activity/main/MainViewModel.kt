package com.rbt.cryptocompare.cryptocompareapp.activity.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rbt.cryptocompare.cryptocompareapp.db.CoinDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel(), IMainViewModel {

    private val mainModel = MutableLiveData<MainDataModel>()
    private var db: CoinDatabase? = null

    override fun getMainDataObservable() = mainModel

    override fun setDbInstance(db: CoinDatabase) { this.db = db }

    override fun getMainData() {
        viewModelScope.launch(Dispatchers.IO) {
            val dbModel = db?.coinDao()?.getAll()
            val mainModelList = dbModel?.map { MainDataModel.CoinItem.getInstanceFromDbModel(it) }?.toTypedArray()
                ?: emptyArray()

            withContext(Dispatchers.Main) { mainModel.postValue(MainDataModel(mainModelList)) }
        }
    }
}