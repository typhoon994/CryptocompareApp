package com.typhoon.cryptocompare.cryptocompareapp.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.typhoon.cryptocompare.cryptocompareapp.data.networking.model.ComparisonResponse
import org.jetbrains.annotations.NotNull

@Entity
class ComparisonDbModel(@PrimaryKey @NotNull val Symbol: String,
                        val BTC: String,
                        val ETH: String,
                        val EVN: String,
                        val DOGE: String,
                        val ZEC: String,
                        val USD: String,
                        val EUR: String) {

    companion object {
        fun getInstanceFromComparisonResponse(symbol: String, response: ComparisonResponse): ComparisonDbModel {
            return ComparisonDbModel(symbol,
                    response.BTC,
                    response.ETH,
                    response.EVN,
                    response.DOGE,
                    response.ZEC,
                    response.USD,
                    response.EUR)
        }
    }
}