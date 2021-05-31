package com.rbt.cryptocompare.cryptocompareapp.activity.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.rbt.cryptocompare.cryptocompareapp.R
import com.rbt.cryptocompare.cryptocompareapp.activity.main.MainDataModel
import com.rbt.cryptocompare.cryptocompareapp.domain.model.CoinItem

class DetailsActivity : AppCompatActivity() {

    lateinit var coin: CoinItem

    companion object {

        private val COIN_EXTRA = "coin_extra"

        fun newIntent(context: Context, coin: CoinItem): Intent {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra(COIN_EXTRA, coin)

            return intent;
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        coin = intent.extras?.getParcelable(COIN_EXTRA)!!
        findViewById<ViewPager>(R.id.view_pager).adapter =
            DetailsPagerAdapter(supportFragmentManager, coin)
    }
}
