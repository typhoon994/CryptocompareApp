package com.rbt.cryptocompare.cryptocompareapp.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.rbt.cryptocompare.cryptocompareapp.R
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewModel: IMainViewModel

    private val observer: Observer<MainDataModel>
        get() = Observer { model ->
            viewAdapter = MainAdapter(model!!.list)
            recyclerView.adapter = viewAdapter

            loader.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewManager = LinearLayoutManager(this)
        viewManager.orientation = LinearLayoutManager.VERTICAL

        recyclerView = findViewById<RecyclerView>(R.id.list).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            visibility = View.GONE
        }

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.getMainDataObservable().observe(this, observer)

        loader.visibility = View.VISIBLE
        Thread(Runnable { viewModel.getMainData() }).start()
    }
}
