package com.typhoon.cryptocompare.cryptocompareapp.domain.abstraction

import com.typhoon.cryptocompare.cryptocompareapp.domain.model.CoinItem

interface CoinRepository {
    suspend fun cacheData()
    suspend fun getAll(): List<CoinItem>
}