package com.rbt.cryptocompare.cryptocompareapp.activity.details.model

import com.rbt.cryptocompare.cryptocompareapp.networking.model.HistoryResponse
import java.util.*

class HistoryModel(val historyItems: Array<HistoryItem>, val startTime: Date, val endTime: Date, val unit: String) {

    companion object {
        fun getInstanceFromNetworkModel(model: HistoryResponse, unit: String) : HistoryModel {
            val items = model.Data.sortedBy { it.time }.map { HistoryItem.getInstanceFromNetworkModel(it) }
            val startTime = model.Data.last().time
            val endTime = model.Data.first().time

            return HistoryModel(items.toTypedArray(), Date(startTime*1000), Date(endTime*1000), unit)
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