package com.rbt.cryptocompare.cryptocompareapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CoinDbModel::class, ComparisonDbModel::class], version = 9)
abstract class CoinDatabase : RoomDatabase() {
    abstract fun coinDao(): CoinDao
}