package com.rbt.cryptocompare.cryptocompareapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface CoinDao {
    @Query("SELECT * FROM CoinDbModel") fun getAll(): Array<CoinDbModel>

    @Insert(onConflict = OnConflictStrategy.IGNORE) fun insertAll(coins: Array<CoinDbModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE) fun insertComparison(comparison: ComparisonDbModel)

    @Query("SELECT * FROM ComparisonDbModel WHERE Symbol LIKE :symbol") fun getComparison(symbol: String) : ComparisonDbModel
}