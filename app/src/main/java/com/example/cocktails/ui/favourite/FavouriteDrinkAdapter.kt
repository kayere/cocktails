package com.example.cocktails.ui.favourite


import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktails.data.models.FavouriteDrink
import com.example.cocktails.databinding.DrinkListItemBinding
import com.example.cocktails.loadUrl
import com.example.cocktails.ui.drinks.DrinksFragment
import com.example.cocktails.ui.drinks.IngredientImageAdapter

class FavouriteDrinkAdapter(
    private val drinks: List<FavouriteDrink>,
    private val fragment: DrinksFragment,
    private val viewModel: FavouriteViewModel
) :
    RecyclerView.Adapter<FavouriteDrinkAdapter.FavouriteDrinkViewHolder>() {
    private var finishedAnimation = false
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteDrinkViewHolder =
        FavouriteDrinkViewHolder(
            DrinkListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: FavouriteDrinkViewHolder, position: Int) {
        val drink = drinks[position]
        holder.binding.apply {
            drinkThumb.loadUrl(drink.drinkThumb, fragment.requireContext())
            drinkName.text = drink.drinkName
            drinkGlass.text = drink.glass
            instructions.text = drink.instructions

            viewModel.fetchIngredients(drink).observe(fragment) {
                ingredientScroll.adapter = IngredientImageAdapter(it, fragment.requireContext())
            }
            if (position <= 3 && !finishedAnimation) {
                root.alpha = 0F
                val fade = PropertyValuesHolder.ofFloat(View.ALPHA, 1F)
                val moveUp = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, 500F, 0F)
                ObjectAnimator.ofPropertyValuesHolder(root, fade, moveUp).apply {
                    duration = (200 * (position + 1)).toLong()
                }.start()
            }
            if (position == 6) {
                finishedAnimation = true
            }
        }
    }

    override fun getItemCount(): Int = drinks.size

    inner class FavouriteDrinkViewHolder(val binding: DrinkListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

}