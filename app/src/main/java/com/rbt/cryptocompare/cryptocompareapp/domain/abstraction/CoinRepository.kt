package com.rbt.cryptocompare.cryptocompareapp.domain.abstraction

import com.rbt.cryptocompare.cryptocompareapp.domain.model.CoinItem

interface CoinRepository {

    suspend fun cacheData()
    suspend fun getAll(): List<CoinItem>
}