package com.typhoon.cryptocompare.cryptocompareapp.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.typhoon.cryptocompare.cryptocompareapp.R
import com.typhoon.cryptocompare.cryptocompareapp.domain.model.CoinHistory
import com.typhoon.cryptocompare.cryptocompareapp.domain.model.CoinItem
import com.typhoon.cryptocompare.cryptocompareapp.presentation.details.viewmodel.DetailsViewModel
import com.typhoon.cryptocompare.cryptocompareapp.presentation.details.viewmodel.IHistoryViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HistoryFragment : DetailsFragment() {
    private val viewModel: IHistoryViewModel by viewModels<DetailsViewModel>()
    private lateinit var coinItem: CoinItem
    private var spinnerRate: Spinner? = null
    private var spinnerInterval: Spinner? = null
    private var historyGraph: HistoryGraphView? = null

    companion object {
        private val COIN_EXTRA = "coin_extra"
        fun newInstance(coinItem: CoinItem): DetailsFragment {
            val fragment = HistoryFragment()
            val bundle = Bundle()
            bundle.putParcelable(COIN_EXTRA, coinItem)

            fragment.arguments = bundle
            return fragment
        }

    }

    override fun getTabTitle() = "Coin History"

    private val rateListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(p0: AdapterView<*>?) {
        }

        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            val adapter: ArrayAdapter<CharSequence?> = when (spinnerRate!!.selectedItem) {
                "Day" -> {
                    ArrayAdapter.createFromResource(
                        requireContext(),
                        R.array.day_rate_interval, android.R.layout.simple_spinner_item
                    )
                }

                "Hour" -> {
                    ArrayAdapter.createFromResource(
                        requireContext(),
                        R.array.hour_rate_interval, android.R.layout.simple_spinner_item
                    )
                }

                else -> {
                    ArrayAdapter.createFromResource(
                        requireContext(),
                        R.array.minute_rate_interval, android.R.layout.simple_spinner_item
                    )
                }
            }

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            requireView().findViewById<Spinner>(R.id.spinner_interval).adapter = adapter
        }
    }

    private val intervalListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(p0: AdapterView<*>?) {
        }

        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            viewModel.onComparisonCriteriaChanged(
                coinItem.Symbol,
                spinnerInterval!!.selectedItem as String,
                spinnerRate!!.selectedItem as String
            )
        }
    }

    private val historyObserver: Observer<CoinHistory>
        get() = Observer { coinHistory: CoinHistory? ->
            coinHistory?.let {
                historyGraph!!.coinHistory = it
                historyGraph!!.invalidate()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        coinItem = arguments?.getParcelable(COIN_EXTRA) ?: error("Coin extra expected")

        viewModel.historyData.observe(viewLifecycleOwner, historyObserver)

        val view = inflater.inflate(R.layout.fragment_history, container, false)
        spinnerRate = view.findViewById(R.id.spinner_rate)
        spinnerInterval = view.findViewById(R.id.spinner_interval)
        historyGraph = view.findViewById(R.id.history_graph)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.history_rate, android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerRate!!.adapter = adapter
        spinnerInterval!!.onItemSelectedListener = intervalListener
        spinnerRate!!.onItemSelectedListener = rateListener
    }
}