package com.rbt.cryptocompare.cryptocompareapp.data.networking.model

class CoinsResponse(val BaseImageUrl: String, val Data: Map<String, CoinData>) {

    class CoinData(val Id: String,
                   val ImageUrl: String,
                   val CoinName: String,
                   val Symbol: String,
                   val FullName: String,
                   val Algorithm: String,
                   val ProofType: String,
                   val TotalCoinSupply: String,
                   val PreMinedValue: String,
                   val TotalCoinsFreeFloat: String,
                   val IsTrading: String)
}