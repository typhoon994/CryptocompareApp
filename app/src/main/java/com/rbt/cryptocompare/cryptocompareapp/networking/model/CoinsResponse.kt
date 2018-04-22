package com.rbt.cryptocompare.cryptocompareapp.networking.model

class CoinsResponse(val BaseImageUrl: String, val Data: Map<String, CoinData>) {

    class CoinData(val Id: String, val ImageUrl: String, val CoinName: String, val Symbol: String);
}