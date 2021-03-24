package com.example.cocktails.ui.splash_screen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.cocktails.data.Repository
import com.example.cocktails.data.models.Drink
import com.example.cocktails.data.models.Ingredient
import com.example.cocktails.work.Work

class SplashFragmentViewModel(private val repository: Repository, private val context: Context) :
    ViewModel() {

    suspend fun checkDb() {
        if (repository.drinks().size < 400) {
            val wm = WorkManager.getInstance(context)
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
            val work = OneTimeWorkRequest.Builder(Work::class.java)
                .setConstraints(constraints)
                .build()
            wm.enqueue(work)
        }
        else repository.getHomeIngredients()
    }
}

class SplashFragmentViewModelFactory(
    private val repository: Repository,
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SplashFragmentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SplashFragmentViewModel(repository, context) as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }

}