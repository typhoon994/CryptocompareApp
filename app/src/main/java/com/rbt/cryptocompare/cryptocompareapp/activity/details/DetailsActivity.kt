package com.rbt.cryptocompare.cryptocompareapp.activity.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.rbt.cryptocompare.cryptocompareapp.R
import com.rbt.cryptocompare.cryptocompareapp.activity.main.MainDataModel
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {

    lateinit var coin: MainDataModel.CoinItem

    companion object {

        private val COIN_EXTRA = "coin_extra"

        fun newIntent(context: Context, coin: MainDataModel.CoinItem) : Intent {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra(COIN_EXTRA, coin)

            return intent;
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        coin = intent.extras.getParcelable(COIN_EXTRA)
        view_pager.adapter = DetailsPagerAdapter(supportFragmentManager, coin)
    }
}
