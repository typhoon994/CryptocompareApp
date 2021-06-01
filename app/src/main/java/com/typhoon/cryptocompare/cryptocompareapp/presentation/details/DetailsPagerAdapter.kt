package com.typhoon.cryptocompare.cryptocompareapp.presentation.details

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.typhoon.cryptocompare.cryptocompareapp.domain.model.CoinItem

class DetailsPagerAdapter(fm: FragmentManager, coin: CoinItem) : FragmentPagerAdapter(fm)  {

        private val screens = arrayListOf(MoreDetailsFragment.newInstance(coin), HistoryFragment.newInstance(coin))

        override fun getItem(position: Int) = screens[position]

        override fun getCount() = screens.size

        override fun getPageTitle(position: Int) = screens[position].getTabTitle()
}