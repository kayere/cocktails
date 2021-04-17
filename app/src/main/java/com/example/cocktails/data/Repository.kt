package com.example.cocktails.data

import com.example.cocktails.data.local.DrinksDao
import com.example.cocktails.data.local.FavouriteDrinkDao
import com.example.cocktails.data.local.IngredientsDao
import com.example.cocktails.data.models.*
import com.example.cocktails.data.network.CocktailDbService
import kotlinx.coroutines.flow.Flow
import java.util.*

class Repository(
    private val drinksDao: DrinksDao,
    private val ingredientsDao: IngredientsDao,
    private val favouriteDrinkDao: FavouriteDrinkDao
) {
    private val api = CocktailDbService.api

    suspend fun getDrink(id: String): Drink = drinksDao.getDrink(id)

    suspend fun getDrinks(key: String): Drinks = api.getDrinks(key)

    suspend fun addDrink(drink: Drink) = drinksDao.addDrink(drink)

    private var drinks: List<Drink> = emptyList()
    suspend fun drinks(): List<Drink> {
        if (drinks.isEmpty()) drinks = drinksDao.getDrinks()
        return drinks
    }

    suspend fun searchDrink(key: String): Drinks = api.searchDrink(key)

    suspend fun lookupIngredient(id: Int): Ingredients = api.lookupIngredient(id)

    suspend fun addIngredient(ingredient: Ingredient) = ingredientsDao.addIngredient(ingredient)

    fun getIngredients(): Flow<List<Ingredient>> = ingredientsDao.getIngredients()

    var ingredients = emptyList<Ingredient>()
    suspend fun getHomeIngredients(): List<Ingredient> {
        if (ingredients.isEmpty()) ingredients = ingredientsDao.getHomeIngredients()
        return ingredients
    }


    fun filterDrinkByCategory(category: String): Flow<List<Drink>> =
        drinksDao.filterDrinkByCategory(category)

    fun filterDrinkByAlcohol(alcohol: String): Flow<List<Drink>> =
        drinksDao.filterDrinkByAlcohol(alcohol)

    suspend fun filterHomeDrinkByCategory(category: String): List<Drink> =
        drinksDao.filterHomeDrinksByCategory(category)

    suspend fun getAlcoholicDrinks(alcohol: String): List<Drink> =
        drinksDao.getAlcoholicDrinks(alcohol)

    suspend fun getIngredientByName(name: String): Ingredient? =
        ingredientsDao.getIngredientByName(name.toLowerCase(Locale.ROOT))

    suspend fun searchIngredient(name: String): Ingredients = api.searchIngredient(name)

    fun getDrinksWithIngredient(ingredient: String): Flow<List<Drink>> =
        drinksDao.getDrinksWithIngredient(ingredient.toLowerCase(Locale.ROOT))

    suspend fun getFavourites(): List<FavouriteDrink> = favouriteDrinkDao.getFavourites()

    suspend fun addFavourite(favouriteDrink: FavouriteDrink) = favouriteDrinkDao.addFavourite(favouriteDrink)
}