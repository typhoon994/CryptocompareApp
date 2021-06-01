package com.typhoon.cryptocompare.cryptocompareapp.domain.model

enum class HistoryInterval(val timeInMinutes: Int) {
    Hour(60),
    ThreeHours(3 * 60),
    Day(24 * 60),
    ThreeDays(3 * 24 * 60),
    Week(7 * 24 * 60),
    TwoWeeks(2 * 7 * 24 * 60),
    Month(30 * 24 * 60);

    companion object {
        fun getFromDisplayText(displayText: String): HistoryInterval {
            return valueOf(displayText.replace(" ", ""))
        }
    }
}