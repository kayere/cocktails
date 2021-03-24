package com.example.cocktails.ui.drink_detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cocktails.databinding.ActivityDrinkImageBinding
import com.example.cocktails.loadUrl

class DrinkImageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDrinkImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.drinkThumb.loadUrl(intent.getStringExtra("imageUrl"), this)
    }
}