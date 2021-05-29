package com.example.cocktails.ui.drinks

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktails.data.models.Drink
import com.example.cocktails.data.models.DrinkMap
import com.example.cocktails.databinding.DrinkListItemBinding
import com.example.cocktails.loadUrl

class DrinksAdapter(
    private val drinks: List<Drink>,
    private val fragment: DrinksFragment,
    private val viewModel: DrinksFragmentViewModel,
    private val drinkType: String
) :
    RecyclerView.Adapter<DrinksAdapter.DrinksViewHolder>() {
    private var finishedAnimation = false
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinksViewHolder =
        DrinksViewHolder(
            DrinkListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: DrinksViewHolder, position: Int) {
        val drink = drinks[position]
        holder.binding.apply {
            drinkThumb.apply {
                loadUrl(drink.drinkThumb, fragment.requireContext())
                transitionName = "drink at $position"
            }
            drinkName.text = drink.drinkName
            drinkGlass.text = drink.glass
            instructions.text = drink.instructions
            drinkCard.setOnClickListener {
                val extras = FragmentNavigatorExtras(drinkThumb to "detail page $position")
                val args = DrinkMap(drinkType, position)
                fragment.findNavController().navigate(
                    DrinksFragmentDirections.actionDrinksFragmentToDrinkDetailFragment(args), extras
                )
            }

            viewModel.fetchIngredients(drink).observe(fragment) {
                ingredientScroll.adapter = IngredientImageAdapter(it, fragment.requireContext())
            }
            if (position <= 3 && !finishedAnimation && fragment.doAnimation) {
                root.alpha = 0F
                val fade = PropertyValuesHolder.ofFloat(View.ALPHA, 1F)
                val moveUp = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, 500F, 0F)
                ObjectAnimator.ofPropertyValuesHolder(root, fade, moveUp).apply {
                    duration = (200 * (position + 1)).toLong()
                }.start()
            }
            if (position >= 6) {
                finishedAnimation = true
                fragment.doAnimation = false
            }
        }
    }

    override fun getItemCount(): Int = drinks.size

    inner class DrinksViewHolder(val binding: DrinkListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

}