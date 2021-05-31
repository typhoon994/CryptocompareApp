package com.rbt.cryptocompare.cryptocompareapp.presentation.main

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.rbt.cryptocompare.cryptocompareapp.R
import com.rbt.cryptocompare.cryptocompareapp.domain.model.CoinItem
import com.squareup.picasso.Picasso

class MainAdapter(
    private val mainData: List<CoinItem>,
    private val listener: IOnCoinSelectedListener?
) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    interface IOnCoinSelectedListener {
        fun onCoinSelected(coin: CoinItem)
    }

    class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun render(model: CoinItem, listener: IOnCoinSelectedListener?) {
            itemView.findViewById<TextView>(R.id.name).text = model.Name
            itemView.findViewById<TextView>(R.id.symbol).text = model.Symbol
            Picasso.get().load(model.ImageUrl).into(itemView.findViewById<ImageView>(R.id.icon))

            itemView.setOnClickListener { listener?.onCoinSelected(model) }
        }
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.render(mainData[position], listener)
    }

    override fun getItemCount() = mainData.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_main, parent, false)
        return MainViewHolder(itemView)
    }
}