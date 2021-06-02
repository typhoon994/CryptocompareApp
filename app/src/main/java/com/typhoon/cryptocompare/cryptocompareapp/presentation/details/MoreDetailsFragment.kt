package com.typhoon.cryptocompare.cryptocompareapp.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Picasso
import com.typhoon.cryptocompare.cryptocompareapp.R
import com.typhoon.cryptocompare.cryptocompareapp.domain.model.CoinComparison
import com.typhoon.cryptocompare.cryptocompareapp.domain.model.CoinItem
import com.typhoon.cryptocompare.cryptocompareapp.presentation.details.viewmodel.DetailsViewModel
import com.typhoon.cryptocompare.cryptocompareapp.presentation.details.viewmodel.IMoreDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MoreDetailsFragment : DetailsFragment() {

    private val viewModel: IMoreDetailsViewModel by viewModels<DetailsViewModel>()
    private lateinit var coinItem: CoinItem
    private var coinComparison: TextView? = null
    private var coinSymbol: TextView? = null
    private var comparisonLayout: View? = null
    private var loader: View? = null

    companion object {
        private const val COIN_EXTRA = "coin_extra"
        private const val SHOW_NEXT = "show_next"

        fun newInstance(coinItem: CoinItem): DetailsFragment {
            val fragment = MoreDetailsFragment()
            val bundle = Bundle()
            bundle.putParcelable(COIN_EXTRA, coinItem)

            fragment.arguments = bundle
            return fragment
        }

    }

    override fun getTabTitle() = "More details"

    private val comparisonDataObserver: Observer<CoinComparison?>
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
        val historyButtonVisible = arguments?.getBoolean(SHOW_NEXT) ?: false

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
        val historyButton = view.findViewById<Button>(R.id.show_history)
        historyButton.isVisible = historyButtonVisible
        historyButton.setOnClickListener { onHistoryButtonClick() }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        coinSymbol!!.text = String.format(getString(R.string.coin_format), coinItem.Symbol)

        viewModel.comparisonDataObservable.observe(viewLifecycleOwner, comparisonDataObserver)
        viewModel.onViewShown(coinItem.Symbol)

        comparisonLayout!!.visibility = View.GONE
        loader!!.visibility = View.VISIBLE
    }

    private fun onHistoryButtonClick() {
        val action = MoreDetailsFragmentDirections.showHistory(coinItem)
        findNavController().navigate(action)
    }
}