package com.typhoon.cryptocompare.cryptocompareapp.presentation.navigationdetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.viewpager.widget.ViewPager
import com.typhoon.cryptocompare.cryptocompareapp.R
import com.typhoon.cryptocompare.cryptocompareapp.domain.model.CoinItem
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NavigationDetailsActivity : AppCompatActivity() {
    companion object {
        private val COIN_EXTRA = "coin_extra"
        private val SHOW_NEXT = "show_next"

        fun newIntent(context: Context, coin: CoinItem): Intent {
            return Intent(context, NavigationDetailsActivity::class.java).apply {
                putExtra(COIN_EXTRA, coin)
                putExtra(SHOW_NEXT, true)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation_details)

        Navigation.findNavController(this, R.id.nav_host_fragment)
            .setGraph(R.navigation.nav_graph, intent.extras)
    }
}
