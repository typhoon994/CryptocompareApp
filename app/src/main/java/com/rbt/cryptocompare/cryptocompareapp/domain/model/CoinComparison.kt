package com.rbt.cryptocompare.cryptocompareapp.domain.model

import com.rbt.cryptocompare.cryptocompareapp.data.db.ComparisonDbModel

class CoinComparison(val BTC: String,
                     val ETH: String,
                     val EVN: String,
                     val DOGE: String,
                     val ZEC: String,
                     val USD: String,
                     val EUR: String) {

    companion object {
        fun getInstanceFromDBModel(model: ComparisonDbModel) : CoinComparison {
            return CoinComparison(model.BTC + " BTC",
                    model.ETH + " ETH",
                    model.EVN + " EVN",
                    model.DOGE + " DOGE",
                    model.ZEC + " ZEC",
                    model.USD + " USD",
                    model.EUR + " EUR")
        }
    }

    override fun toString(): String {
        val results = StringBuilder()
        results.append(BTC)
        results.append(",\n")
        results.append(DOGE)
        results.append(",\n")
        results.append(ETH)
        results.append(",\n")
        results.append(EUR)
        results.append(",\n")
        results.append(EVN)
        results.append(",\n")
        results.append(USD)
        results.append(",\n")
        results.append(ZEC)
        results.append(".")

        return results.toString()
    }
}