package com.example.cocktails.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktails.data.models.Ingredient
import com.example.cocktails.databinding.IngredientItemBinding
import com.example.cocktails.loadUrl

class IngredientAdapter(
    var ingredients: List<Ingredient>,
    private val navController: NavController
) :
    RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder =
        IngredientViewHolder(IngredientItemBinding.inflate(LayoutInflater.from(parent.context)))


    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        holder.binding.apply {
            ingredientName.apply {
                text = ingredients[position].name
                transitionName = "Ingredient${ingredients[position].ingredientId} name"
            }
            ingredientThumb.apply {
                loadUrl(ingredients[position].thumb, context)
                transitionName = "Ingredient${ingredients[position].ingredientId} image"
            }
            ingredientCard.setOnClickListener {
                val options =
                    HomeFragmentDirections.actionHomeFragmentToIngredientDetailFragment(ingredients[position])
                val extras = FragmentNavigatorExtras(
                    ingredientThumb to "Ingredient image",
                    ingredientName to "Ingredient name"
                )
                navController.navigate(options, extras)
            }
        }
    }

    override fun getItemCount(): Int = if (ingredients.size > 20) 20 else ingredients.size

    inner class IngredientViewHolder(val binding: IngredientItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}