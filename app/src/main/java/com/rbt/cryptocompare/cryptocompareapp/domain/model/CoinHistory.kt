package com.rbt.cryptocompare.cryptocompareapp.domain.model

import com.rbt.cryptocompare.cryptocompareapp.data.networking.model.HistoryResponse
import java.util.*

class CoinHistory(val historyItems: Array<HistoryItem>, val startTime: Date, val endTime: Date, val unit: String) {

    companion object {
        fun getInstanceFromNetworkModel(model: HistoryResponse, unit: String) : CoinHistory {
            val items = model.Data.sortedBy { it.time }.map { HistoryItem.getInstanceFromNetworkModel(it) }
            val startTime = model.Data.last().time
            val endTime = model.Data.first().time

            return CoinHistory(items.toTypedArray(), Date(startTime*1000), Date(endTime*1000), unit)
        }
    }

    class HistoryItem(val value: Float) {
        companion object {
            fun getInstanceFromNetworkModel(model: HistoryResponse.HistoryItemResponse) : HistoryItem {
                return HistoryItem(model.high.toFloat())
            }
        }
    }
}