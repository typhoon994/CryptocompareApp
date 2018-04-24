package com.rbt.cryptocompare.cryptocompareapp.activity.details

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rbt.cryptocompare.cryptocompareapp.R
import com.rbt.cryptocompare.cryptocompareapp.activity.main.MainDataModel
import android.arch.lifecycle.ViewModelProviders
import android.arch.persistence.room.Room
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.rbt.cryptocompare.cryptocompareapp.activity.details.model.HistoryModel
import com.rbt.cryptocompare.cryptocompareapp.activity.details.viewmodel.DetailsViewModel
import com.rbt.cryptocompare.cryptocompareapp.activity.details.viewmodel.IHistoryViewModel
import com.rbt.cryptocompare.cryptocompareapp.db.CoinDatabase
import kotlinx.android.synthetic.main.fragment_history.*


class HistoryFragment : DetailsFragment() {

    override fun getTabTitle() = "Coin History"
    private lateinit var viewModel: IHistoryViewModel
    private lateinit var coinItem: MainDataModel.CoinItem

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
            val adapter: ArrayAdapter<CharSequence?>?

            when (interval_spinner.selectedItem) {
                "Day" -> {
                    adapter = ArrayAdapter.createFromResource(context,
                            R.array.day_rate_interval, android.R.layout.simple_spinner_item)
                }

                "Hour" -> {
                    adapter = ArrayAdapter.createFromResource(context,
                            R.array.hour_rate_interval, android.R.layout.simple_spinner_item)
                }

                else -> {
                    adapter = ArrayAdapter.createFromResource(context,
                            R.array.minute_rate_interval, android.R.layout.simple_spinner_item)
                }
            }

            adapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            rate_spinner.adapter = adapter
        }
    }

    private val rateListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(p0: AdapterView<*>?) {
        }

        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            viewModel.getComparisonResults(coinItem.Symbol,
                    interval_spinner.selectedItem as String,
                    rate_spinner.selectedItem as String).observe(this@HistoryFragment, observer)
        }
    }

    private val observer: Observer<HistoryModel>
        get() = Observer { model: HistoryModel? ->
            model?.let {
                history_graph.model = it
                history_graph.invalidate()
            }
        }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        coinItem = arguments.getParcelable(COIN_EXTRA)
        val db = Room.databaseBuilder(context,
                CoinDatabase::class.java, "coin-db")
                .fallbackToDestructiveMigration()
                .build()
        viewModel = ViewModelProviders.of(this).get(DetailsViewModel::class.java)
        viewModel.setDBInstance(db)

        return inflater?.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        val adapter = ArrayAdapter.createFromResource(context,
                R.array.history_rate, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        interval_spinner.adapter = adapter
        rate_spinner.onItemSelectedListener = rateListener
        interval_spinner.onItemSelectedListener = intervalListener
    }
}