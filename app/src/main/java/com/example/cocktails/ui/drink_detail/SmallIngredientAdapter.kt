package com.example.cocktails.ui.drink_detail

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.animation.doOnEnd
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktails.data.models.Ingredient
import com.example.cocktails.databinding.SmallIngredientItemBinding
import com.example.cocktails.loadUrl

class SmallIngredientAdapter(var ingredients: List<Ingredient>, private val context: Context) :
    RecyclerView.Adapter<SmallIngredientAdapter.SmallIngredientViewHolder>() {

    private val fade = PropertyValuesHolder.ofFloat(View.ALPHA, 0F, 1F)
    private val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 0.5F, 1F)
    private val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 0.5F, 1F)
    private val animatedValues = mutableListOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SmallIngredientViewHolder =
        SmallIngredientViewHolder(SmallIngredientItemBinding.inflate(LayoutInflater.from(parent.context)))


    override fun onBindViewHolder(holder: SmallIngredientViewHolder, position: Int) {
        holder.binding.apply {
            ingredientName.text = ingredients[position].name
            ingredientThumb.loadUrl(ingredients[position].thumb, context)
            ingredientCard.setOnClickListener {

            }
            if (!animatedValues.contains(position)) {
                ObjectAnimator.ofPropertyValuesHolder(root, fade, scaleX, scaleY).apply {
                    duration = 200L
                    interpolator = AccelerateDecelerateInterpolator()
                    doOnEnd { root.alpha = 1F }
                }.start()
                animatedValues.add(position)
            } else root.alpha = 1F
        }
    }

    override fun getItemCount(): Int = if (ingredients.size > 20) 20 else ingredients.size

    inner class SmallIngredientViewHolder(val binding: SmallIngredientItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}