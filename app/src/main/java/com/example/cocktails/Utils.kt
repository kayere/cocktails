package com.example.cocktails

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.cocktails.data.Repository
import com.example.cocktails.data.local.DrinksDb
import com.example.cocktails.data.models.Drink

// EXTENSION FUNCTIONS
// Get ingredient names from the drink
fun Drink.getIngredientNames(): Set<String> =
    this.toString().split(", ")
        .filter { it.contains("ingredient") && !it.contains("instructions") && !it.contains("null") }
        .map {
            if (it.split("=").size == 2) it.split("=")[1]
            else ""
        }
        .toSet()

fun ImageView.loadUrl(url: String?, context: Context) =
    Glide.with(context)
        .load(url)
        .placeholder(getProgressDrawable(context))
        .error(R.drawable.ic_image_failed)
        .transition(DrawableTransitionOptions.withCrossFade(200))
        .into(this)


fun animatePropertyValuesHolder(
    views: List<View>,
    vararg values: PropertyValuesHolder
): AnimatorSet {
    val animators = mutableListOf<Animator>()
    for (view in views) animators.add(ObjectAnimator.ofPropertyValuesHolder(view, *values).apply {
        duration = 300L
        interpolator = DecelerateInterpolator()
    })
    val animSet = AnimatorSet()
    animSet.playTogether(animators)
    return animSet
}

const val query = "SELECT * FROM drinks_table WHERE :ingredient IN " +
        "(LOWER(ingredient1), " +
        "LOWER(ingredient2), " +
        "LOWER(ingredient3), " +
        "LOWER(ingredient4), " +
        "LOWER(ingredient5), " +
        "LOWER(ingredient6), " +
        "LOWER(ingredient7), " +
        "LOWER(ingredient8), " +
        "LOWER(ingredient9), " +
        "LOWER(ingredient10), " +
        "LOWER(ingredient11), " +
        "LOWER(ingredient12), " +
        "LOWER(ingredient13), " +
        "LOWER(ingredient14), " +
        "LOWER(ingredient15))"

private var repository: Repository? = null
fun getRepository(context: Context): Repository {
    if (repository == null) {
        val db = DrinksDb.getDatabase(context)
        repository = Repository(db.drinkDao(), db.ingredientsDao())
    }
    return repository!!
}

fun getProgressDrawable(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 5f
        centerRadius = 20f
        setColorSchemeColors(Color.RED)
        start()
    }
}

// Check for internet connection
fun checkConnection(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    return true
                }
            }
        }
    } else {
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
            return true
        }
    }
    return false
}


