package com.typhoon.cryptocompare.cryptocompareapp.presentation.main

import androidx.lifecycle.LiveData

interface IMainViewModel {
    val mainDataObservable : LiveData<MainDataModel>
    fun onViewShown()
}