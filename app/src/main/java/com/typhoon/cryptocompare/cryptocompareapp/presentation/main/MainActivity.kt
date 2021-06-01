package com.typhoon.cryptocompare.cryptocompareapp.presentation.main

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.typhoon.cryptocompare.cryptocompareapp.R
import com.typhoon.cryptocompare.cryptocompareapp.domain.model.CoinItem
import com.typhoon.cryptocompare.cryptocompareapp.presentation.details.DetailsActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MainAdapter.IOnCoinSelectedListener {
    private val viewModel: IMainViewModel by viewModels<MainViewModel>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private var loader: View? = null

    private val mainDataObserver: Observer<MainDataModel>
        get() = Observer { model ->
            viewAdapter = MainAdapter(model!!.list, this)
            recyclerView.adapter = viewAdapter

            loader!!.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loader = findViewById(R.id.loader)

        val viewManager = LinearLayoutManager(this)
        viewManager.orientation = LinearLayoutManager.VERTICAL

        recyclerView = findViewById<RecyclerView>(R.id.list).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            visibility = View.GONE
        }

        viewModel.mainDataObservable.observe(this, mainDataObserver)

        loader!!.visibility = View.VISIBLE
        viewModel.onViewShown()
    }

    override fun onCoinSelected(coin: CoinItem) {
        val intent = DetailsActivity.newIntent(this, coin)
        startActivity(intent)
    }
}
