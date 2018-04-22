package com.rbt.cryptocompare.cryptocompareapp.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


@Dao
interface CoinDao {
    @Query("SELECT * FROM CoinDbModel") fun getAll(): Array<CoinDbModel>

//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " + "last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): User

    @Insert(onConflict = OnConflictStrategy.IGNORE) fun insertAll(coins: Array<CoinDbModel>)
}