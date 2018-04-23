package com.rbt.cryptocompare.cryptocompareapp.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = arrayOf(CoinDbModel::class, ComparisonDbModel::class), version = 4)
abstract class CoinDatabase : RoomDatabase() {

    abstract fun coinDao(): CoinDao
}