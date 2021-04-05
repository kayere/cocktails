package com.example.cocktails.ui.drinks

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktails.data.models.Ingredient
import com.example.cocktails.databinding.IngredientImageBinding
import com.example.cocktails.loadUrl
import com.example.cocktails.loadUrlWithoutPlaceholder

class IngredientImageAdapter(
    private val ingredients: List<Ingredient>,
    private val context: Context
) : RecyclerView.Adapter<IngredientImageAdapter.IngredientImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientImageViewHolder =
        IngredientImageViewHolder(
            IngredientImageBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: IngredientImageViewHolder, position: Int) {
        holder.binding.imageView.loadUrlWithoutPlaceholder(ingredients[position].thumb, context)
    }

    override fun getItemCount(): Int = ingredients.size

    inner class IngredientImageViewHolder(val binding: IngredientImageBinding) :
        RecyclerView.ViewHolder(binding.root)
}