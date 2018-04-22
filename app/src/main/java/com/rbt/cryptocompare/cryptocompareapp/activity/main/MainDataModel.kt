package com.rbt.cryptocompare.cryptocompareapp.activity.main

class MainDataModel(val list: Array<CoinItem>) {
    class CoinItem(val Name: String, val Symbol: String, val ImageUrl: String)
}