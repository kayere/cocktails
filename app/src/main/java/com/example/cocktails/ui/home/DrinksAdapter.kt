package com.example.cocktails.ui.home

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.OvershootInterpolator
import android.widget.Toast
import androidx.core.animation.doOnEnd
import androidx.navigation.NavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktails.data.models.Drink
import com.example.cocktails.databinding.DrinkItemBinding
import com.example.cocktails.loadUrl

class DrinksAdapter(
    var drinks: List<Drink>,
    private var navController: NavController,
    private val context: Context
) :
    RecyclerView.Adapter<DrinksViewHolder>() {

    private val fade = PropertyValuesHolder.ofFloat(View.ALPHA, 0F, 1F)
    private val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 0.2F, 1F)
    private val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 0.2F, 1F)
    private var clickedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinksViewHolder =
        DrinksViewHolder(DrinkItemBinding.inflate(LayoutInflater.from(parent.context)))

    override fun onBindViewHolder(holder: DrinksViewHolder, position: Int) {
        holder.binding.apply {
            root.transitionName = "drink ${drinks[position].drinkId} on $position to detail"
            root.setOnClickListener {
                clickedPosition = position
                val options =
                    HomeFragmentDirections.actionHomeFragmentToDrinkDetailFragment(drinks[position])
                val extras = FragmentNavigatorExtras(root to "detail page")
                navController.navigate(options, extras)
            }
            drinkName.text = drinks[position].drinkName
            drinkGlass.text = drinks[position].glass
            drinkThumb.loadUrl(drinks[position].drinkThumb, context)
            root.alpha = 1F
            ObjectAnimator.ofPropertyValuesHolder(root, fade, scaleX, scaleY).apply {
                duration = 300L
                interpolator = OvershootInterpolator()
                startDelay = 150L
                doOnEnd { root.alpha = 1F }
            }
            if (position == clickedPosition) {
                root.translationY = -20F
                ObjectAnimator.ofFloat(root, View.TRANSLATION_Y, 0F).apply {
                    duration = 300L
                    startDelay = 300L
                    interpolator = DecelerateInterpolator()
                }.start()
            }
        }
    }

    override fun getItemCount(): Int = if (drinks.size > 20) 20 else drinks.size
}

class DrinksViewHolder(val binding: DrinkItemBinding) : RecyclerView.ViewHolder(binding.root)