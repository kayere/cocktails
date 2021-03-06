package com.example.cocktails.ui.drink_detail

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.*
import com.example.cocktails.data.Repository
import com.example.cocktails.data.models.Drink
import com.example.cocktails.data.models.Ingredient
import com.example.cocktails.getCocktails
import com.example.cocktails.getHomeDrinks
import com.example.cocktails.getIngredientNames
import com.example.cocktails.getOrdinaryDrinks
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flow

class DrinkDetailFragmentViewModel(
    private val repository: Repository,
    private val context: Context
) : ViewModel() {

    suspend fun drinks() = repository.drinks()

    suspend fun homeDrinks() = getHomeDrinks(context)
    suspend fun cocktails() = getCocktails(context)
    suspend fun ordinaryDrinks() = getOrdinaryDrinks(context)

    fun fetchIngredients(drink: Drink) = flow {
        val ingredients = mutableSetOf<Ingredient>()
        val unAvailableIngredients = mutableListOf<String>()
        // fetching ingredients from the database using their names
        val ingredientNames = drink.getIngredientNames()
        for (name in ingredientNames) {
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