package com.typhoon.cryptocompare.cryptocompareapp.presentation.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.typhoon.cryptocompare.cryptocompareapp.R
import com.typhoon.cryptocompare.cryptocompareapp.presentation.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private val viewModel: ISplashViewModel by viewModels<SplashViewModel>()

    companion object {
        private const val SPLASH_TIME = 500L
    }

    private fun onSplashTimeExpired() = viewModel.onViewShown()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        viewModel.dismissSplashObservable.observe(this, {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        })
        Handler().postDelayed(this::onSplashTimeExpired, SPLASH_TIME)
    }
}
