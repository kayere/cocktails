package com.example.cocktails.ui.ingredient_detail

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktails.data.models.Drink
import com.example.cocktails.databinding.SmallDrinkItemBinding
import com.example.cocktails.loadUrl

class SmallDrinkAdapter(var drinks: List<Drink>, private val context: Context) :
    RecyclerView.Adapter<SmallDrinkAdapter.SmallDrinkViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SmallDrinkViewHolder =
        SmallDrinkViewHolder(SmallDrinkItemBinding.inflate(LayoutInflater.from(parent.context)))

    override fun onBindViewHolder(holder: SmallDrinkViewHolder, position: Int) {
        holder.binding.apply {
            drinkCard.transitionName = "card$position to detail"
            drinkCard.setOnClickListener {

            }
            drinkName.text = drinks[position].drinkName
            drinkGlass.text = drinks[position].glass
            drinkThumb.loadUrl(drinks[position].drinkThumb, context)
        }
    }

    override fun getItemCount(): Int = if (drinks.size > 20) 20 else drinks.size

    inner class SmallDrinkViewHolder(val binding: SmallDrinkItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}