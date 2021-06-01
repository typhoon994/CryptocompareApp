package com.typhoon.cryptocompare.cryptocompareapp.domain.model

enum class HistoryRate(val timeInMinutes: Int) {
    Minute(1),
    Hour(60),
    Day(24 * 60);

    companion object {
        fun getFromDisplayText(displayText: String): HistoryRate {
            return valueOf(displayText.replace(" ", ""))
        }
    }
}