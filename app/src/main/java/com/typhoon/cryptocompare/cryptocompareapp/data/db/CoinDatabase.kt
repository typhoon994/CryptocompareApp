package com.typhoon.cryptocompare.cryptocompareapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CoinDbModel::class, ComparisonDbModel::class], version = 9, exportSchema = false)
abstract class CoinDatabase : RoomDatabase() {
    abstract fun coinDao(): CoinDao
}