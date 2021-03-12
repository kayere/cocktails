package com.example.cocktails.ui.drink_detail

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.cocktails.data.Repository
import com.example.cocktails.data.models.Drink
import com.example.cocktails.data.models.Ingredient
import com.example.cocktails.getIngredientNames
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flow

class DrinkDetailFragmentViewModel(
    private val repository: Repository,
    private val context: Context
) : ViewModel() {

    var drink: Drink? = null

    val fetchIngredients = flow {
        val ingredients = mutableSetOf<Ingredient>()
        val unAvailableIngredients = mutableListOf<String>()
        Log.d("TAG", ": ${drink?.getIngredientNames()} \n $drink")
        // fetching ingredients from the database using their names
        for (name in drink?.getIngredientNames()!!) {
            val ingredient = repository.getIngredientByName(name)
            if (ingredient != null) ingredients.add(ingredient)
            else unAvailableIngredients.add(name)
        }
        emit(ingredients.toList())

        unAvailableIngredients.map { unAvailableIngredient ->
            val ingredient: Ingredient? = viewModelScope.async(Dispatchers.IO) {
                var ingredient: Ingredient? = null
                try {
                    ingredient =
                        repository.searchIngredient(unAvailableIngredient).ingredients?.get(0)
                } catch (e: Exception) {
                    return@async ingredient
                }
                ingredient?.let {
                    ingredient.thumb =
                        "https://www.thecocktaildb.com/images/ingredients/${ingredient.name}-medium.png"
                    repository.addIngredient(ingredient)
                }
                return@async ingredient
            }.await()
            if (ingredient != null) {
                ingredients.add(ingredient)
                emit(ingredients.toList())
            } else {
                Toast.makeText(context, "Could not load all ingredients", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }.asLiveData()

}

class DrinkDetailFragmentViewModelFactory(
    private val repository: Repository,
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DrinkDetailFragmentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DrinkDetailFragmentViewModel(repository, context) as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }

}