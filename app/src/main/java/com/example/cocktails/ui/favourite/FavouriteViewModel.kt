package com.example.cocktails.ui.favourite

import androidx.lifecycle.*
import com.example.cocktails.data.Repository
import com.example.cocktails.data.models.FavouriteDrink
import com.example.cocktails.data.models.Ingredient
import com.example.cocktails.getIngredientNames
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.flow

class FavouriteViewModel(private val repository: Repository) : ViewModel() {

    val favouriteDrinks: LiveData<List<FavouriteDrink>> = repository.getFavourites().asLiveData()

    fun fetchIngredients(drink: FavouriteDrink) = flow {
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
            ingredient?.let {
                ingredients.add(ingredient)
                emit(ingredients.toList())
            }
        }
    }.asLiveData()
}

class FavouriteViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(FavouriteViewModel::class.java))
            return FavouriteViewModel(repository) as T
        else throw IllegalArgumentException("Unknown viewModel class")
    }

}