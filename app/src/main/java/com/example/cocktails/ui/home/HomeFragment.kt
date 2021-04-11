package com.example.cocktails.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.cocktails.*
import com.example.cocktails.databinding.FragmentHomeBinding
import com.google.android.material.transition.MaterialElevationScale
import com.google.android.material.transition.MaterialSharedAxis
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeFragmentViewModel
    private lateinit var drinksAdapter: DrinksAdapter
    private lateinit var cocktailAdapter: DrinksAdapter
    private lateinit var ordinaryDrinkAdapter: DrinksAdapter
    private lateinit var ingredientAdapter: IngredientAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)
        exitTransition = MaterialElevationScale(false)
        reenterTransition = MaterialElevationScale(true)
        viewModel =
            ViewModelProvider(
                this,
                HomeFragmentViewModelFactory(getRepository(requireContext()), requireContext())
            )
                .get(HomeFragmentViewModel::class.java)

        runBlocking {
            drinksAdapter =
                DrinksAdapter(
                    viewModel.getHomeDrinks(),
                    findNavController(),
                    requireContext(),
                    DrinkTypes.HOME.toString()
                )
            cocktailAdapter =
                DrinksAdapter(
                    viewModel.getCocktails(),
                    findNavController(),
                    requireContext(),
                    DrinkTypes.COCKTAILS.toString()
                )
            ordinaryDrinkAdapter =
                DrinksAdapter(
                    viewModel.getOrdinaryDrinks(),
                    findNavController(),
                    requireContext(),
                    DrinkTypes.ORDINARY.toString()
                )
            ingredientAdapter = IngredientAdapter(viewModel.getIngredients(), findNavController())
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
        binding.apply {
            drinks.adapter = drinksAdapter
            cocktails.adapter = cocktailAdapter
            ordinaryDrinks.adapter = ordinaryDrinkAdapter
            ingredients.adapter = ingredientAdapter
            toolBar.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.settings -> {
                        val options =
                            HomeFragmentDirections.actionHomeFragmentToSettingsFragment()
                        findNavController().navigate(options)
                        true
                    }
                    else -> false
                }
            }
            all.setOnClickListener {
                val extras = FragmentNavigatorExtras(binding.drinks to "drink list")
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToDrinksFragment(
                        DrinkTypes.ALL.toString()
                    ), extras
                )
            }
            more.setOnClickListener {
                val extras = FragmentNavigatorExtras(binding.drinks to "drink list")
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToDrinksFragment(
                        DrinkTypes.ALL.toString()
                    ), extras
                )
            }
            cocktailsLabel.setOnClickListener {
                val extras = FragmentNavigatorExtras(binding.cocktails to "drink list")
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToDrinksFragment(
                        DrinkTypes.COCKTAILS.toString()
                    ), extras
                )
            }
            moreCocktails.setOnClickListener {
                val extras = FragmentNavigatorExtras(binding.cocktails to "drink list")
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToDrinksFragment(
                        DrinkTypes.COCKTAILS.toString()
                    ), extras
                )
            }
            ordinaryLabel.setOnClickListener {
                val extras = FragmentNavigatorExtras(binding.ordinaryDrinks to "drink list")
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToDrinksFragment(
                        DrinkTypes.ORDINARY.toString()
                    ), extras
                )
            }
            moreOrdinaryDrinks.setOnClickListener {
                val extras = FragmentNavigatorExtras(binding.ordinaryDrinks to "drink list")
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToDrinksFragment(
                        DrinkTypes.ORDINARY.toString()
                    ), extras
                )
            }
        }
    }

    override fun onPause() {
        super.onPause()

    }

    override fun onResume() {
        super.onResume()

    }

}