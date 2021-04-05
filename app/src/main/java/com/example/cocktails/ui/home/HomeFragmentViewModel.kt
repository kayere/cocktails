package com.example.cocktails.ui.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cocktails.data.Repository
import com.example.cocktails.data.models.Drink
import com.example.cocktails.data.models.Ingredient
import com.example.cocktails.getCocktails
import com.example.cocktails.getHomeDrinks
import com.example.cocktails.getOrdinaryDrinks
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HomeFragmentViewModel(private val repository: Repository, private val context: Context) :
    ViewModel() {

    suspend fun getHomeDrinks() = getHomeDrinks(context)
    suspend fun getCocktails() = getCocktails(context)
    suspend fun getOrdinaryDrinks() = getOrdinaryDrinks(context)

    private var ingredients: List<Ingredient>? = null
    suspend fun getIngredients(): List<Ingredient> {
        if (ingredients == null)
            ingredients = repository.getHomeIngredients().shuffled()
        return ingredients!!
    }

    suspend fun alcoholDrinks(): List<Drink> =
        withContext(Dispatchers.IO) { repository.getAlcoholicDrinks("Non Alcoholic") }

    suspend fun nonAlcoholDrinks(): List<Drink> =
        withContext(Dispatchers.IO) { repository.getAlcoholicDrinks("Non Alcoholic") }

}

class HomeFragmentViewModelFactory(
    private val repository: Repository,
    private val context: Context
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeFragmentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeFragmentViewModel(repository, context) as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }

}