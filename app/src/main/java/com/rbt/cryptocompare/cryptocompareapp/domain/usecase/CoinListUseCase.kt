package com.rbt.cryptocompare.cryptocompareapp.domain.usecase

import com.rbt.cryptocompare.cryptocompareapp.data.db.CoinDatabase
import com.rbt.cryptocompare.cryptocompareapp.data.repository.CoinRepositoryImpl
import com.rbt.cryptocompare.cryptocompareapp.domain.model.CoinItem

class CoinListUseCase(private val database: CoinDatabase?) {

    suspend fun sync() {
        CoinRepositoryImpl(database = database).cacheData()
    }

    suspend fun getAll(): List<CoinItem> {
        return CoinRepositoryImpl(database = database).getAll()
    }
}