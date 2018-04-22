package com.rbt.cryptocompare.cryptocompareapp.activity.details

import android.support.v4.app.Fragment

abstract class DetailsFragment : Fragment() {

    abstract fun getTabTitle() : String
}