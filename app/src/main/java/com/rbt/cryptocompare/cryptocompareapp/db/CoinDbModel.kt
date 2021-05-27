package com.rbt.cryptocompare.cryptocompareapp.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rbt.cryptocompare.cryptocompareapp.networking.model.CoinsResponse

@Entity
class CoinDbModel(@PrimaryKey val Id: Long,
                  @ColumnInfo(name = "name") val Name: String,
                  @ColumnInfo(name = "symbol") val Symbol: String,
                  @ColumnInfo(name = "imageUrl") val ImageUrl: String,
                  @ColumnInfo(name = "full_name") val FullName: String,
                  @ColumnInfo(name = "algorithm") val Algorithm: String,
                  @ColumnInfo(name = "proof_type") val ProofType: String,
                  @ColumnInfo(name = "total_supply") val TotalCoinSupply: String?,
                  @ColumnInfo(name = "pre_mined_value") val PreMinedValue: String?,
                  @ColumnInfo(name = "total_coins_free_float") val TotalCoinsFreeFloat: String?,
                  @ColumnInfo(name = "is_trading") val IsTrading: String?) {

    companion object {
        fun instanceFromNetworkingModel(model: CoinsResponse.CoinData, baseUrl: String) : CoinDbModel {
            val id = model.Id.toLong()
            return CoinDbModel(id, model.CoinName, model.Symbol, baseUrl + model.ImageUrl,
                    model.FullName, model.Algorithm, model.ProofType, model.TotalCoinSupply, model.PreMinedValue,
                    model.TotalCoinsFreeFloat, model.IsTrading)
        }
    }
}