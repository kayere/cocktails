package com.example.cocktails.ui.splash_screen

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.cocktails.data.Repository
import com.example.cocktails.data.local.DrinksDb
import com.example.cocktails.databinding.ActivitySplashBinding
import com.example.cocktails.getRepository
import com.example.cocktails.ui.home.HomeActivity
import com.google.android.material.transition.platform.MaterialSharedAxis
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val binding = ActivitySplashBinding.inflate(layoutInflater)
        window.exitTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true).apply {
            addTarget(binding.root)
            duration = 300L
        }
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val viewModel = ViewModelProvider(this, SplashActivityViewModelFactory(getRepository(this), this))
            .get(SplashActivityViewModel::class.java)

        lifecycleScope.launch {
            viewModel.checkDb()
            delay(1000)
            val options = ActivityOptions.makeSceneTransitionAnimation(this@SplashActivity)
            val homeActivityIntent = Intent(this@SplashActivity, HomeActivity::class.java)
            startActivity(homeActivityIntent, options.toBundle())
            delay(500)
            finish()
        }
    }
}