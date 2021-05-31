package com.rbt.cryptocompare.cryptocompareapp.activity.main

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.room.Room
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import com.rbt.cryptocompare.cryptocompareapp.R
import com.rbt.cryptocompare.cryptocompareapp.activity.details.DetailsActivity
import com.rbt.cryptocompare.cryptocompareapp.db.CoinDatabase
import com.rbt.cryptocompare.cryptocompareapp.domain.model.CoinItem


class MainActivity : AppCompatActivity(), MainAdapter.IOnCoinSelectedListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewModel: IMainViewModel
    private var loader: View? = null

    private val observer: Observer<MainDataModel>
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

        val db = Room.databaseBuilder(applicationContext,
                CoinDatabase::class.java, "coin-db")
                .fallbackToDestructiveMigration()
                .build()

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.setDbInstance(db)
        viewModel.getMainDataObservable().observe(this, observer)

        loader!!.visibility = View.VISIBLE
        viewModel.onViewShown()
    }

    override fun onCoinSelected(coin: CoinItem) {
        val intent = DetailsActivity.newIntent(this, coin)
        startActivity(intent)
    }
}
