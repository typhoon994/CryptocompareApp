package com.typhoon.cryptocompare.cryptocompareapp.domain.usecase

import com.typhoon.cryptocompare.cryptocompareapp.domain.abstraction.CoinRepository
import com.typhoon.cryptocompare.cryptocompareapp.domain.model.CoinItem
import javax.inject.Inject


class CoinListUseCase @Inject constructor(private val coinRepository: CoinRepository) {

    suspend fun sync() {
        coinRepository.cacheData()
    }

    suspend fun getAll(): List<CoinItem> {
        return coinRepository.getAll()
    }
}