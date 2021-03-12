package com.example.cocktails

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Context
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import coil.load
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

fun ImageView.loadUrl(url: String?) {
    this.load(url) {
        placeholder(R.drawable.loader)
        error(R.drawable.ic_image_failed)
    }
}

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