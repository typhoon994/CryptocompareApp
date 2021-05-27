package com.rbt.cryptocompare.cryptocompareapp.activity.details

import androidx.fragment.app.Fragment

abstract class DetailsFragment : Fragment() {

    abstract fun getTabTitle() : String
}