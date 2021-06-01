package com.typhoon.cryptocompare.cryptocompareapp.data.di

import com.typhoon.cryptocompare.cryptocompareapp.data.repository.CoinRepositoryImpl
import com.typhoon.cryptocompare.cryptocompareapp.data.repository.ComparisonRepositoryImpl
import com.typhoon.cryptocompare.cryptocompareapp.domain.abstraction.CoinRepository
import com.typhoon.cryptocompare.cryptocompareapp.domain.abstraction.ComparisonRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class BindModule {
    @Binds
    abstract fun bindCoinRepository(coinRepositoryImpl: CoinRepositoryImpl): CoinRepository

    @Binds
    abstract fun bindHistoryRepository(comparisonRepositoryImpl: ComparisonRepositoryImpl): ComparisonRepository
}