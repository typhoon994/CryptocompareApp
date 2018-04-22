package com.rbt.cryptocompare.cryptocompareapp.db

import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.Database

@Database(entities = arrayOf(CoinDbModel::class), version = 1)
abstract class CoinDatabase : RoomDatabase() {

    abstract fun coinDao(): CoinDao
}