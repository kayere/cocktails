package com.example.cocktails.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.cocktails.R
import com.example.cocktails.data.Repository
import com.example.cocktails.data.local.DrinksDb
import com.example.cocktails.databinding.FragmentHomeBinding
import com.google.android.material.transition.MaterialElevationScale
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition = MaterialElevationScale(false)
        reenterTransition = MaterialElevationScale(true)
        val db = DrinksDb.getDatabase(this.requireContext())
        val repository = Repository(db.drinkDao(), db.ingredientsDao())
        viewModel =
            ViewModelProvider(this, HomeActivityViewModelFactory(repository, this.requireContext()))
                .get(HomeActivityViewModel::class.java)
        runBlocking {
            if (viewModel.homeDrinks == null) viewModel.homeDrinks = viewModel.getHomeDrinks()
            if (viewModel.cocktails == null) viewModel.cocktails = viewModel.getCocktails()
            if (viewModel.ordinaryDrinks == null) viewModel.ordinaryDrinks = viewModel.getOrdinaryDrinks()
            if (viewModel.ingredients == null) viewModel.ingredients = viewModel.getIngredients()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }
        lifecycleScope.launch {
            binding.apply {
                drinks.adapter = DrinksAdapter(viewModel.homeDrinks!!.shuffled(), findNavController())
                cocktails.adapter = DrinksAdapter(viewModel.cocktails!!.shuffled(), findNavController())
                ordinaryDrinks.adapter =
                    DrinksAdapter(viewModel.ordinaryDrinks!!.shuffled(), findNavController())
                ingredients.adapter =
                    IngredientAdapter(viewModel.ingredients!!.shuffled(), findNavController())
                toolBar.setOnMenuItemClickListener { menuItem ->
                    when(menuItem.itemId) {
                        R.id.settings -> {
                            val options = HomeFragmentDirections.actionHomeFragmentToSettingsFragment()
                            findNavController().navigate(options)
                            true
                        }
                        else -> false
                    }
                }
            }
        }
    }

}