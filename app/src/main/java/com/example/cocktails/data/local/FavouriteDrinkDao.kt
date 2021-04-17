package com.example.cocktails.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.cocktails.data.models.FavouriteDrink

@Dao
interface FavouriteDrinkDao {
    @Query("SELECT * FROM favourite_drink")
    suspend fun getFavourites(): List<FavouriteDrink>
    @Insert
    suspend fun addFavourite(favouriteDrink: FavouriteDrink)
}