package com.example.cocktails.ui.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cocktails.data.Repository
import com.example.cocktails.data.models.Drink
import com.example.cocktails.data.models.Ingredient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HomeActivityViewModel(private val repository: Repository, private val context: Context) :
    ViewModel() {

    suspend fun getHomeDrinks(): List<Drink> = withContext(Dispatchers.IO) { repository.drinks() }
    var homeDrinks: List<Drink>? = null

    suspend fun alcoholDrinks(): List<Drink> =
        withContext(Dispatchers.IO) { repository.getAlcoholicDrinks("Non Alcoholic") }

    suspend fun nonAlcoholDrinks(): List<Drink> =
        withContext(Dispatchers.IO) { repository.getAlcoholicDrinks("Non Alcoholic") }

    suspend fun getCocktails(): List<Drink> =
        withContext(Dispatchers.IO) { repository.filterHomeDrinkByCategory("Cocktail") }

    var cocktails: List<Drink>? = null

    suspend fun getOrdinaryDrinks(): List<Drink> =
        withContext(Dispatchers.IO) { repository.filterHomeDrinkByCategory("Ordinary Drink") }

    var ordinaryDrinks: List<Drink>? = null

    suspend fun getIngredients(): List<Ingredient> =
        withContext(Dispatchers.IO) { repository.getHomeIngredients() }
    var ingredients: List<Ingredient>? = null
}

class HomeActivityViewModelFactory(
    private val repository: Repository,
    private val context: Context
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeActivityViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeActivityViewModel(repository, context) as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }

}