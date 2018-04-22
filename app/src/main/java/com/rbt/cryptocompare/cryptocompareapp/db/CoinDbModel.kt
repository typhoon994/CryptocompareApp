package com.rbt.cryptocompare.cryptocompareapp.db

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
class CoinDbModel(@PrimaryKey val Id: Long,
               @ColumnInfo(name = "name") val Name: String,
               @ColumnInfo(name = "symbol") val Symbol: String,
               @ColumnInfo(name = "imageUrl") val ImageUrl: String)