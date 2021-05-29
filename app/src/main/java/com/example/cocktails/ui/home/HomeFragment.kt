package com.example.cocktails.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.SharedElementCallback
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.example.cocktails.DrinkTypes
import com.example.cocktails.R
import com.example.cocktails.databinding.FragmentHomeBinding
import com.example.cocktails.getRepository
import com.google.android.material.transition.MaterialElevationScale
import com.google.android.material.transition.MaterialSharedAxis
import kotlinx.coroutines.runBlocking

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeFragmentViewModel
    private lateinit var drinksAdapter: DrinksAdapter
    private lateinit var cocktailAdapter: DrinksAdapter
    private lateinit var ordinaryDrinkAdapter: DrinksAdapter
    private lateinit var ingredientAdapter: IngredientAdapter
    var homeDrinksPosition = -1
    var cocktailDrinksPosition = -1
    var ordinaryDrinksPosition = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)
        exitTransition = MaterialElevationScale(false)
        reenterTransition = MaterialElevationScale(true)
        setExitSharedElementCallback(object : SharedElementCallback() {
            override fun onMapSharedElements(
                names: MutableList<String>?,
                sharedElements: MutableMap<String, View>?
            ) {
                super.onMapSharedElements(names, sharedElements)
                when {
                    homeDrinksPosition >= 0 ->
                        names?.get(0)?.let { name ->
                            binding.drinks.findViewHolderForAdapterPosition(homeDrinksPosition)?.itemView?.let { itemView ->
                                sharedElements?.put(
                                    name,
                                    itemView
                                )
                            }
                        }
                    cocktailDrinksPosition >= 0 ->
                        names?.get(0)?.let { name ->
                            binding.cocktails.findViewHolderForAdapterPosition(cocktailDrinksPosition)?.itemView?.let { itemView ->
                                sharedElements?.put(
                                    name,
                                    itemView
                                )
                            }
                        }
                    else ->
                        names?.get(0)?.let { name ->
                            binding.ordinaryDrinks.findViewHolderForAdapterPosition(ordinaryDrinksPosition)?.itemView?.let { itemView ->
                                sharedElements?.put(
                                    name,
                                    itemView
                                )
                            }
                        }
                }

            }
        })
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
                    this@HomeFragment,
                    DrinkTypes.HOME.toString()
                )
            cocktailAdapter =
                DrinksAdapter(
                    viewModel.getCocktails(),
                    this@HomeFragment,
                    DrinkTypes.COCKTAILS.toString()
                )
            ordinaryDrinkAdapter =
                DrinksAdapter(
                    viewModel.getOrdinaryDrinks(),
                    this@HomeFragment,
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
                    R.id.favorites -> {
                        findNavController().navigate(
                            HomeFragmentDirections.actionHomeFragmentToFavouriteFragment()
                        )
                        true
                    }
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
                refreshPositions()
                val extras = FragmentNavigatorExtras(binding.drinks to "drink list")
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToDrinksFragment(
                        DrinkTypes.ALL.toString()
                    ), extras
                )
            }
            more.setOnClickListener {
                refreshPositions()
                val extras = FragmentNavigatorExtras(binding.drinks to "drink list")
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToDrinksFragment(
                        DrinkTypes.ALL.toString()
                    ), extras
                )
            }
            cocktailsLabel.setOnClickListener {
                refreshPositions()
                val extras = FragmentNavigatorExtras(binding.cocktails to "drink list")
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToDrinksFragment(
                        DrinkTypes.COCKTAILS.toString()
                    ), extras
                )
            }
            moreCocktails.setOnClickListener {
                refreshPositions()
                val extras = FragmentNavigatorExtras(binding.cocktails to "drink list")
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToDrinksFragment(
                        DrinkTypes.COCKTAILS.toString()
                    ), extras
                )
            }
            ordinaryLabel.setOnClickListener {
                refreshPositions()
                val extras = FragmentNavigatorExtras(binding.ordinaryDrinks to "drink list")
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToDrinksFragment(
                        DrinkTypes.ORDINARY.toString()
                    ), extras
                )
            }
            moreOrdinaryDrinks.setOnClickListener {
                refreshPositions()
                val extras = FragmentNavigatorExtras(binding.ordinaryDrinks to "drink list")
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToDrinksFragment(
                        DrinkTypes.ORDINARY.toString()
                    ), extras
                )
            }
        }
    }

    fun refreshPositions() {
        homeDrinksPosition = -1
        cocktailDrinksPosition = -1
        ordinaryDrinksPosition = -1
    }
}