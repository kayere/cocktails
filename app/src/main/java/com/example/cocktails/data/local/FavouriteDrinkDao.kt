package com.example.cocktails.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.cocktails.data.models.FavouriteDrink
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteDrinkDao {
    @Query("SELECT * FROM favourite_drink")
    fun getFavourites(): Flow<List<FavouriteDrink>>
    @Insert
    suspend fun addFavourite(favouriteDrink: FavouriteDrink)
}