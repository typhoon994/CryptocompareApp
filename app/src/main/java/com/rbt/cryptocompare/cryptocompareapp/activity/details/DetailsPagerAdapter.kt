package com.rbt.cryptocompare.cryptocompareapp.activity.details

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.rbt.cryptocompare.cryptocompareapp.activity.main.MainDataModel

class DetailsPagerAdapter(fm: FragmentManager, coin: MainDataModel.CoinItem) : FragmentPagerAdapter(fm)  {

        private val screens = arrayListOf(MoreDetailsFragment.newInstance(coin), HistoryFragment.newInstance(coin))

        override fun getItem(position: Int) = screens[position]

        override fun getCount() = screens.size

        override fun getPageTitle(position: Int) = screens[position].getTabTitle()
}