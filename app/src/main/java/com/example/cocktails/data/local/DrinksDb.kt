package com.example.cocktails.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cocktails.data.models.Drink
import com.example.cocktails.data.models.FavouriteDrink
import com.example.cocktails.data.models.Ingredient

@Database(entities = [Drink::class, Ingredient::class, FavouriteDrink::class], version = 1)
abstract class DrinksDb : RoomDatabase() {
    abstract fun drinkDao(): DrinksDao
    abstract fun ingredientsDao(): IngredientsDao
    abstract fun favouriteDao(): FavouriteDrinkDao

    companion object {
        @Volatile
        private var INSTANCE: DrinksDb? = null

        fun getDatabase(context: Context): DrinksDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DrinksDb::class.java,
                    "drinks_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}