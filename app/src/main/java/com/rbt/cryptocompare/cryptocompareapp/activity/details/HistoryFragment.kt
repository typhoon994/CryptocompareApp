package com.rbt.cryptocompare.cryptocompareapp.activity.details

import androidx.lifecycle.Observer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rbt.cryptocompare.cryptocompareapp.R
import com.rbt.cryptocompare.cryptocompareapp.activity.main.MainDataModel
import androidx.lifecycle.ViewModelProviders
import androidx.room.Room
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.rbt.cryptocompare.cryptocompareapp.activity.details.model.HistoryModel
import com.rbt.cryptocompare.cryptocompareapp.activity.details.viewmodel.DetailsViewModel
import com.rbt.cryptocompare.cryptocompareapp.activity.details.viewmodel.IHistoryViewModel
import com.rbt.cryptocompare.cryptocompareapp.db.CoinDatabase


class HistoryFragment : DetailsFragment() {

    override fun getTabTitle() = "Coin History"
    private lateinit var viewModel: IHistoryViewModel
    private lateinit var coinItem: MainDataModel.CoinItem
    private var intervalSpinner: Spinner? = null
    private var rateSpinner: Spinner? = null
    private var historyGraph: HistoryGraphView? = null

    companion object {
        private val COIN_EXTRA = "coin_extra"

        fun newInstance(coinItem: MainDataModel.CoinItem): DetailsFragment {
            val fragment = HistoryFragment()
            val bundle = Bundle()
            bundle.putParcelable(COIN_EXTRA, coinItem)

            fragment.arguments = bundle
            return fragment
        }
    }

    private val intervalListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(p0: AdapterView<*>?) {
        }

        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            val adapter: ArrayAdapter<CharSequence?> = when (intervalSpinner!!.selectedItem) {
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
            requireView().findViewById<Spinner>(R.id.rate_spinner).adapter = adapter
        }
    }

    private val rateListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(p0: AdapterView<*>?) {
        }

        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            viewModel.getComparisonResults(
                coinItem.Symbol,
                intervalSpinner!!.selectedItem as String,
                intervalSpinner!!.selectedItem as String
            ).observe(this@HistoryFragment, observer)
        }
    }

    private val observer: Observer<HistoryModel>
        get() = Observer { model: HistoryModel? ->
            model?.let {
                historyGraph!!.model = it
                historyGraph!!.invalidate()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        coinItem = arguments?.getParcelable(COIN_EXTRA) ?: error("Coin extra expected")
        val db = Room.databaseBuilder(
            requireContext(),
            CoinDatabase::class.java, "coin-db"
        )
            .fallbackToDestructiveMigration()
            .build()
        viewModel = ViewModelProviders.of(this).get(DetailsViewModel::class.java)
        viewModel.setDBInstance(db)

        val view = inflater.inflate(R.layout.fragment_history, container, false)
        intervalSpinner = view.findViewById(R.id.interval_spinner)
        rateSpinner = view.findViewById(R.id.rate_spinner)
        historyGraph = view.findViewById(R.id.history_graph)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.history_rate, android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        intervalSpinner!!.adapter = adapter
        rateSpinner!!.onItemSelectedListener = rateListener
        intervalSpinner!!.onItemSelectedListener = intervalListener
    }
}