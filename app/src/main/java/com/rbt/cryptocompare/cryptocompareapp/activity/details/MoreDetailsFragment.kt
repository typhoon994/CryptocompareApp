package com.rbt.cryptocompare.cryptocompareapp.activity.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.rbt.cryptocompare.cryptocompareapp.R
import com.rbt.cryptocompare.cryptocompareapp.activity.main.MainDataModel
import com.squareup.picasso.Picasso

class MoreDetailsFragment : DetailsFragment() {

    override fun getTabTitle() = "More details"
    private lateinit var coinItem: MainDataModel.CoinItem

    companion object {
        protected val COIN_EXTRA = "coin_extra"

        fun newInstance(coinItem: MainDataModel.CoinItem): DetailsFragment {
            val fragment = MoreDetailsFragment()
            val bundle = Bundle()
            bundle.putParcelable(COIN_EXTRA, coinItem)

            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_more_details, container, false)
        coinItem = arguments.getParcelable(COIN_EXTRA)

        Picasso.get().load(coinItem.ImageUrl).into(view?.findViewById<ImageView>(R.id.icon))
        view?.findViewById<TextView>(R.id.full_name)?.text = coinItem.FullName
        view?.findViewById<TextView>(R.id.alghoritm)?.text = String.format(getString(R.string.algorithm_format), coinItem.Alghoritm)
        view?.findViewById<TextView>(R.id.proof_type)?.text = String.format(getString(R.string.proof_type_format), coinItem.ProofType)
        view?.findViewById<TextView>(R.id.total_coin_supply)?.text = String.format(getString(R.string.total_coin_supply_format), coinItem.TotalCoinSupply)
        view?.findViewById<TextView>(R.id.pre_mined_value)?.text = String.format(getString(R.string.pre_mined_format), coinItem.PreMinedValue)
        view?.findViewById<TextView>(R.id.total_coins_free_float)?.text = String.format(getString(R.string.total_coins_free_float_format), coinItem.TotalCoinsFreeFloat)
        view?.findViewById<TextView>(R.id.trading)?.text = String.format(getString(R.string.trading_format), coinItem.IsTrading)

        return view
    }
}