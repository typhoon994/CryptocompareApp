package com.rbt.cryptocompare.cryptocompareapp.activity.splash

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.room.Room
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.rbt.cryptocompare.cryptocompareapp.R
import com.rbt.cryptocompare.cryptocompareapp.activity.main.MainActivity
import com.rbt.cryptocompare.cryptocompareapp.db.CoinDatabase


class SplashActivity : AppCompatActivity() {

    companion object {
        private const val SPLASH_TIME = 500L
    }

    private lateinit var viewModel : ISplashViewModel

    private val dismissObserver: Observer<Void?>
        get() = Observer {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

    private fun onSplashTimeExpired() = viewModel.onViewShown()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val db = Room.databaseBuilder(applicationContext,
                CoinDatabase::class.java, "coin-db")
                .fallbackToDestructiveMigration()
                .build()

        viewModel = ViewModelProviders.of(this).get(SplashViewModel::class.java)
        viewModel.dismissSplashObservable.observe(this, dismissObserver)
        viewModel.setDBInstance(db)
        Handler().postDelayed(this::onSplashTimeExpired, SPLASH_TIME)
    }
}
