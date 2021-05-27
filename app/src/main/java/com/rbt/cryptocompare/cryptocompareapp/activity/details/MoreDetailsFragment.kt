package com.rbt.cryptocompare.cryptocompareapp.activity.details

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.room.Room
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.rbt.cryptocompare.cryptocompareapp.R
import com.rbt.cryptocompare.cryptocompareapp.activity.details.model.CoinComparison
import com.rbt.cryptocompare.cryptocompareapp.activity.details.viewmodel.DetailsViewModel
import com.rbt.cryptocompare.cryptocompareapp.activity.details.viewmodel.IMoreDetailsViewModel
import com.rbt.cryptocompare.cryptocompareapp.activity.main.MainDataModel
import com.rbt.cryptocompare.cryptocompareapp.db.CoinDatabase
import com.squareup.picasso.Picasso

class MoreDetailsFragment : DetailsFragment() {

    override fun getTabTitle() = "More details"

    private lateinit var coinItem: MainDataModel.CoinItem
    private lateinit var viewModel: IMoreDetailsViewModel

    private var coinComparison: TextView? = null
    private var coinSymbol: TextView? = null
    private var comparisonLayout: View? = null
    private var loader: View? = null

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

    private val observer: Observer<CoinComparison?>
        get() = Observer { comparison: CoinComparison? ->
            comparison?.let {
                coinComparison!!.text = it.toString()
                comparisonLayout!!.visibility = View.VISIBLE
            }

            loader!!.visibility = View.GONE
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_more_details, container, false)
        coinItem = arguments?.getParcelable(COIN_EXTRA) ?: error("Coin extra is missing")

        Picasso.get().load(coinItem.ImageUrl).into(view.findViewById<ImageView>(R.id.icon))
        view.findViewById<TextView>(R.id.full_name)?.text = coinItem.FullName
        view.findViewById<TextView>(R.id.alghoritm)?.text =
            String.format(getString(R.string.algorithm_format), coinItem.Alghoritm)
        view.findViewById<TextView>(R.id.proof_type)?.text =
            String.format(getString(R.string.proof_type_format), coinItem.ProofType)
        view.findViewById<TextView>(R.id.total_coin_supply)?.text =
            String.format(getString(R.string.total_coin_supply_format), coinItem.TotalCoinSupply)
        view.findViewById<TextView>(R.id.pre_mined_value)?.text =
            String.format(getString(R.string.pre_mined_format), coinItem.PreMinedValue)
        view.findViewById<TextView>(R.id.total_coins_free_float)?.text = String.format(
            getString(R.string.total_coins_free_float_format),
            coinItem.TotalCoinsFreeFloat
        )
        view.findViewById<TextView>(R.id.trading)?.text =
            String.format(getString(R.string.trading_format), coinItem.IsTrading)
        coinComparison = view.findViewById(R.id.coin_comparison)
        comparisonLayout = view.findViewById(R.id.comparison_layout)
        loader = view.findViewById(R.id.loader)
        coinSymbol = view.findViewById(R.id.coin_symbol)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        coinSymbol!!.text = String.format(getString(R.string.coin_format), coinItem.Symbol)

        val db = Room.databaseBuilder(
            requireContext(),
            CoinDatabase::class.java, "coin-db"
        )
            .fallbackToDestructiveMigration()
            .build()

        viewModel = ViewModelProviders.of(this).get(DetailsViewModel::class.java)
        viewModel.setDBInstance(db)
        viewModel.getComparisonResults(coinItem.Symbol).observe(viewLifecycleOwner, observer)

        comparisonLayout!!.visibility = View.GONE
        loader!!.visibility = View.VISIBLE
    }
}