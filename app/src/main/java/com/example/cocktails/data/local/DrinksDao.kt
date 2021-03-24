package com.example.cocktails.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cocktails.data.models.Drink
import com.example.cocktails.query
import kotlinx.coroutines.flow.Flow

@Dao
interface DrinksDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addDrink(drink: Drink)

    @Query("SELECT * FROM drinks_table")
    suspend fun getDrinks(): List<Drink>

    @Query("SELECT * FROM drinks_table WHERE drinkId LIKE :id")
    suspend fun getDrink(id: String): Drink


    @Query("SELECT * FROM drinks_table WHERE alcoholic LIKE :alcohol")
    fun filterDrinkByAlcohol(alcohol: String): Flow<List<Drink>>

    @Query("SELECT * FROM drinks_table WHERE alcoholic LIKE :alcohol")
    suspend fun getAlcoholicDrinks(alcohol: String): List<Drink>

    @Query("SELECT * FROM drinks_table WHERE category LIKE :category")
    fun filterDrinkByCategory(category:String): Flow<List<Drink>>

    @Query("SELECT * FROM drinks_table WHERE category LIKE :category")
    suspend fun filterHomeDrinksByCategory(category:String): List<Drink>

    @Query(query)
    fun getDrinksWithIngredient(ingredient: String): Flow<List<Drink>>
}