package com.rbt.cryptocompare.cryptocompareapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface CoinDao {
    @Query("SELECT * FROM CoinDbModel")
    suspend fun getAll(): Array<CoinDbModel>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(coins: Array<CoinDbModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComparison(comparison: ComparisonDbModel)

    @Query("SELECT * FROM ComparisonDbModel WHERE Symbol LIKE :symbol")
    suspend fun getComparison(symbol: String): ComparisonDbModel
}