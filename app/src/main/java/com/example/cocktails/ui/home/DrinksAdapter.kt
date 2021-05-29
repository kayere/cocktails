package com.example.cocktails.ui.home

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktails.DrinkTypes
import com.example.cocktails.data.models.Drink
import com.example.cocktails.data.models.DrinkMap
import com.example.cocktails.databinding.DrinkItemBinding
import com.example.cocktails.loadUrl

class DrinksAdapter(
    var drinks: List<Drink>,
    private val fragment: HomeFragment,
    private val type: String
) :
    RecyclerView.Adapter<DrinksViewHolder>() {

    private val fade = PropertyValuesHolder.ofFloat(View.ALPHA, 0F, 1F)
    private val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 0.2F, 1F)
    private val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 0.2F, 1F)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinksViewHolder =
        DrinksViewHolder(DrinkItemBinding.inflate(LayoutInflater.from(parent.context)))

    override fun onBindViewHolder(holder: DrinksViewHolder, position: Int) {
        holder.binding.apply {
            root.transitionName = "drink ${drinks[position].drinkId} on $position to detail"
            root.setOnClickListener {
                when (type) {
                    DrinkTypes.HOME.toString() -> {
                        fragment.homeDrinksPosition = position
                        fragment.cocktailDrinksPosition = -1
                        fragment.ordinaryDrinksPosition = -1
                    }
                    DrinkTypes.COCKTAILS.toString() -> {
                        fragment.cocktailDrinksPosition = position
                        fragment.ordinaryDrinksPosition = -1
                        fragment.homeDrinksPosition = -1
                    }
                    else -> {
                        fragment.ordinaryDrinksPosition = position
                        fragment.homeDrinksPosition = -1
                        fragment.cocktailDrinksPosition = -1
                    }
                }
                val options =
                    HomeFragmentDirections.actionHomeFragmentToDrinkDetailFragment(
                        DrinkMap(
                            type,
                            position
                        )
                    )
                val extras = FragmentNavigatorExtras(root to "detail page $position")
                fragment.findNavController().navigate(options, extras)
            }
            drinkName.text = drinks[position].drinkName
            drinkGlass.text = drinks[position].glass
            drinkThumb.loadUrl(drinks[position].drinkThumb, fragment.requireContext())
            root.alpha = 1F
            val animationPosition = when (type) {
                DrinkTypes.HOME.toString() -> fragment.homeDrinksPosition
                DrinkTypes.COCKTAILS.toString() -> fragment.cocktailDrinksPosition
                DrinkTypes.ORDINARY.toString() -> fragment.ordinaryDrinksPosition
                else -> -1
            }
            if (position == animationPosition) {
                root.translationY = -20F
                ObjectAnimator.ofFloat(root, View.TRANSLATION_Y, 0F).apply {
                    startDelay = 300L
                    interpolator = DecelerateInterpolator()
                }.start()
            }
        }
    }

    override fun getItemCount(): Int = if (drinks.size > 20) 20 else drinks.size
}

class DrinksViewHolder(val binding: DrinkItemBinding) : RecyclerView.ViewHolder(binding.root)