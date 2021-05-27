package com.rbt.cryptocompare.cryptocompareapp.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(CoinDbModel::class, ComparisonDbModel::class), version = 7)
abstract class CoinDatabase : RoomDatabase() {

    abstract fun coinDao(): CoinDao
}