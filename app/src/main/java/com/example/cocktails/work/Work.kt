package com.example.cocktails.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.cocktails.getRepository
import kotlinx.coroutines.*

class Work(context: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters) {

    private val repository = getRepository(context)
    override suspend fun doWork(): Result {
        val drinksResult = GlobalScope.async { fetchDrinks() }
        val ingredientResult = GlobalScope.async { fetchIngredients() }

        return if (drinksResult.await() == Result.success() && ingredientResult.await() == Result.success())
            Result.success()
        else Result.failure()
    }

    private suspend fun fetchDrinks(): Result {
        ('b'..'z').map {
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val drinks = repository.getDrinks(it.toString()).drinks
                    drinks?.let {
                        for (drink in drinks) {
                            repository.addDrink(drink)
                        }
                    }

                } catch (e: Exception) {
                }
            }
        }.joinAll()
        return Result.success()
    }

    private suspend fun fetchIngredients(): Result {
        (1..1000).map {
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val ingredients = repository.lookupIngredient(it).ingredients
                    ingredients?.let {
                        ingredients[0].thumb =
                            "https://www.thecocktaildb.com/images/ingredients/${ingredients[0].name}-medium.png"
                        repository.addIngredient(ingredients[0])
                    }
                } catch (e: Exception) {
                }
            }
        }.joinAll()
        return Result.success()
    }

}

