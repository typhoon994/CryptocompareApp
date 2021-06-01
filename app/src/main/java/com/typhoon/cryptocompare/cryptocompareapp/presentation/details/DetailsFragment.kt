package com.typhoon.cryptocompare.cryptocompareapp.presentation.details

import androidx.fragment.app.Fragment

abstract class DetailsFragment : Fragment() {

    abstract fun getTabTitle() : String
}