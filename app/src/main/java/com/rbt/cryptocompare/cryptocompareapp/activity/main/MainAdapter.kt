package com.rbt.cryptocompare.cryptocompareapp.activity.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rbt.cryptocompare.cryptocompareapp.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_main.view.*

class MainAdapter(private val mainData: Array<MainDataModel.CoinItem>,
                  private val listener: IOnCoinSelectedListener?) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    interface IOnCoinSelectedListener {
        fun onCoinSelected(coin: MainDataModel.CoinItem)
    }

    class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun render(model: MainDataModel.CoinItem, listener: IOnCoinSelectedListener?) {
            itemView.name.text = model.Name
            itemView.symbol.text = model.Symbol
            Picasso.get().load(model.ImageUrl).into(itemView.icon)

            itemView.setOnClickListener { listener?.onCoinSelected(model) }
        }
    }

    override fun onBindViewHolder(holder: MainViewHolder?, position: Int) {
        holder?.render(mainData[position], listener)
    }

    override fun getItemCount() = mainData.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MainViewHolder {
        val itemView = LayoutInflater.from(parent!!.context).inflate(R.layout.item_main, parent, false)
        return MainViewHolder(itemView)
    }
}