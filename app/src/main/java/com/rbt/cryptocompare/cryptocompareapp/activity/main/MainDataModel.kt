package com.rbt.cryptocompare.cryptocompareapp.activity.main

import android.os.Parcelable
import com.rbt.cryptocompare.cryptocompareapp.db.CoinDbModel
import kotlinx.android.parcel.Parcelize

class MainDataModel(val list: Array<CoinItem>) {
    @Parcelize @SuppressWarnings("ParcelCreator") class CoinItem(val Id: Long,
                                                                 val Name: String,
                                                                 val Symbol: String,
                                                                 val ImageUrl: String,
                                                                 val FullName: String,
                                                                 val Alghoritm: String,
                                                                 val ProofType: String,
                                                                 val TotalCoinSupply: String,
                                                                 val PreMinedValue: String,
                                                                 val TotalCoinsFreeFloat: String,
                                                                 val IsTrading: String
                                                                 ) : Parcelable {

        companion object {
            fun getInstanceFromDbModel(model: CoinDbModel) : MainDataModel.CoinItem {
                return MainDataModel.CoinItem(model.Id,
                        model.Name,
                        model.Symbol,
                        model.ImageUrl,
                        model.FullName,
                        model.Algorithm,
                        model.ProofType,
                        model.TotalCoinSupply,
                        model.PreMinedValue,
                        model.TotalCoinsFreeFloat,
                        model.IsTrading)
            }
        }
    }
}