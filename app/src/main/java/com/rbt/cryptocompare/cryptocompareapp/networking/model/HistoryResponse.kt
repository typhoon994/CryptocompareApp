package com.rbt.cryptocompare.cryptocompareapp.networking.model

class HistoryResponse(val Data: Array<HistoryItemResponse>) {
    class HistoryItemResponse(val time: Long, val high: Double)
}