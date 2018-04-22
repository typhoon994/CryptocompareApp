package com.rbt.cryptocompare.cryptocompareapp.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rbt.cryptocompare.cryptocompareapp.R
import kotlinx.android.synthetic.main.item_main.view.*

class MainAdapter(private val mainData: Array<MainDataModel.CoinItem>) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun render(model: MainDataModel.CoinItem) {
            itemView.name.text = model.Name
            itemView.symbol.text = model.Symbol
        }
    }

    override fun onBindViewHolder(holder: MainViewHolder?, position: Int) {
        holder?.render(mainData[position])
    }

    override fun getItemCount() = mainData.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MainViewHolder {
        val itemView = LayoutInflater.from(parent!!.context).inflate(R.layout.item_main, parent, false)
        return MainViewHolder(itemView)
    }
}