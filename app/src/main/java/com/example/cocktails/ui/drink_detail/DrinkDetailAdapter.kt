package com.example.cocktails.ui.drink_detail

import android.animation.PropertyValuesHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktails.R
import com.example.cocktails.animatePropertyValuesHolder
import com.example.cocktails.data.models.Drink
import com.example.cocktails.databinding.DrinkDetailBinding
import com.example.cocktails.loadUrl
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DrinkDetailAdapter(
    private val drinks: List<Drink>,
    private val viewModel: DrinkDetailFragmentViewModel,
    private val drinkFragment: DrinkDetailFragment
) :
    RecyclerView.Adapter<DrinkDetailAdapter.DrinkDetailViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinkDetailViewHolder =
        DrinkDetailViewHolder(DrinkDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: DrinkDetailViewHolder, position: Int) {
        holder.binding.apply {
            root.transitionName = "detail page $position"
            instructionsLabel.alpha = 0F
            instructions.alpha = 0F
            ingredientsLabel.alpha = 0F
            drinkThumb.apply {
                loadUrl(drinks[position].drinkThumb, context)
                setOnClickListener {

                }
            }
            drinkName.text = drinks[position].drinkName
            drinkGlass.text = drinks[position].glass
            ingredients.layoutManager = GridLayoutManager(drinkFragment.requireContext(), 3)
        }

        drinkFragment.lifecycleScope.launch {
            delay(400L)
            val fade = PropertyValuesHolder.ofFloat(View.ALPHA, 0F, 1F)
            val moveUp = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, 100F, 0F)
            val animSet = animatePropertyValuesHolder(
                listOf(
                    holder.binding.ingredients, holder.binding.ingredientsLabel,
                    holder.binding.instructionsLabel, holder.binding.instructions
                ), fade, moveUp
            )
            animSet.start()

            var firstFetch = true
            val adapter = SmallIngredientAdapter(emptyList(), drinkFragment.requireContext())
            viewModel.fetchIngredients(drinks[position]).observe(drinkFragment) {
                if (firstFetch) {
                    firstFetch = false
                    when (it.size) {
                        0 -> {
                            holder.binding.apply {
                                ingredientsLabel.text =
                                    drinkFragment.getString(R.string.ingredient_absent)
                                instructionsLabel.text =
                                    drinkFragment.getString(R.string.instructions)
                                instructions.text = drinks[position].instructions
                            }
                            animSet.start()
                        }
                        1 -> {
                            holder.binding.apply {
                                ingredientsLabel.text = drinkFragment.getString(R.string.ingredient)
                                adapter.ingredients = it
                                ingredients.adapter = adapter
                                instructionsLabel.text =
                                    drinkFragment.getString(R.string.instructions)
                                instructions.text = drinks[position].instructions
                            }
                            animSet.start()
                        }
                        else -> {
                            holder.binding.apply {
                                ingredientsLabel.text =
                                    drinkFragment.getString(R.string.ingredients)
                                adapter.ingredients = it
                                ingredients.adapter = adapter
                                instructionsLabel.text =
                                    drinkFragment.getString(R.string.instructions)
                                instructions.text = drinks[position].instructions
                            }
                            animSet.start()
                        }
                    }
                } else {
                    if (it.size > 1) {
                        holder.binding.ingredientsLabel.text =
                            drinkFragment.getString(R.string.ingredients)
                        adapter.ingredients = it
                        holder.binding.ingredients.adapter!!.notifyDataSetChanged()
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int = drinks.size

    inner class DrinkDetailViewHolder(val binding: DrinkDetailBinding) :
        RecyclerView.ViewHolder(binding.root)
}
