package com.typhoon.cryptocompare.cryptocompareapp.data.di

import android.content.Context
import androidx.room.Room
import com.typhoon.cryptocompare.cryptocompareapp.data.db.CoinDatabase
import com.typhoon.cryptocompare.cryptocompareapp.data.networking.ICryptoApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): CoinDatabase {
        return Room.databaseBuilder(context, CoinDatabase::class.java, "coin-db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(): ICryptoApi {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://min-api.cryptocompare.com/data/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ICryptoApi::class.java)
    }
}