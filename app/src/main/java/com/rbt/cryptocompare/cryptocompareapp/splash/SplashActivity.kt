package com.rbt.cryptocompare.cryptocompareapp.splash

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.rbt.cryptocompare.cryptocompareapp.main.MainActivity
import com.rbt.cryptocompare.cryptocompareapp.R

class SplashActivity : AppCompatActivity() {

    companion object {
        private val SPLASH_TIME = 500L
    }

    private lateinit var viewModel : ISplashViewModel

    private val observer: Observer<String?>
        get() = Observer { error: String? ->
            error?.let { Toast.makeText(this, error, Toast.LENGTH_SHORT).show() }
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

    private fun onSplashTimeExpired() = viewModel.cacheMainData().observe(this, observer)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        viewModel = ViewModelProviders.of(this).get(SplashViewModel::class.java)
        Handler().postDelayed(this::onSplashTimeExpired, SPLASH_TIME)
    }
}
