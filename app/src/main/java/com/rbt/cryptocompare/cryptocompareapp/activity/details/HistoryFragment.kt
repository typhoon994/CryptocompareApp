package com.rbt.cryptocompare.cryptocompareapp.activity.details

import android.os.Bundle
import com.rbt.cryptocompare.cryptocompareapp.activity.main.MainDataModel

class HistoryFragment : DetailsFragment() {

    override fun getTabTitle() = "Coin History"

    companion object {
        protected val COIN_EXTRA = "coin_extra"

        fun newInstance(coinItem: MainDataModel.CoinItem): DetailsFragment {
            val fragment = HistoryFragment()
            val bundle = Bundle()
            bundle.putParcelable(COIN_EXTRA, coinItem)

            fragment.arguments = bundle
            return fragment
        }
    }
}