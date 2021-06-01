package com.typhoon.cryptocompare.cryptocompareapp.domain.model

import android.os.Parcelable
import com.typhoon.cryptocompare.cryptocompareapp.data.db.CoinDbModel
import kotlinx.parcelize.Parcelize

@Parcelize
class CoinItem(
    val Id: Long,
    val Name: String,
    val Symbol: String,
    val ImageUrl: String,
    val FullName: String,
    val Alghoritm: String,
    val ProofType: String,
    val TotalCoinSupply: String,
    val PreMinedValue: String,
    val TotalCoinsFreeFloat: String,
    val IsTrading: String?
) : Parcelable {

    companion object {
        fun getInstanceFromDbModel(model: CoinDbModel) = CoinItem(
            model.Id,
            model.Name,
            model.Symbol,
            model.ImageUrl,
            model.FullName,
            model.Algorithm,
            model.ProofType,
            model.TotalCoinSupply ?: "Unknown",
            model.PreMinedValue ?: "Unknown",
            model.TotalCoinsFreeFloat ?: "Unknown",
            model.IsTrading
        )
    }
}