package com.example.cocktails.ui.drink_detail

import android.content.res.Configuration
import android.graphics.Color.TRANSPARENT
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.cocktails.DrinkTypes
import com.example.cocktails.data.models.Drink
import com.example.cocktails.databinding.FragmentDrinkDetailBinding
import com.example.cocktails.getRepository
import com.example.cocktails.ui.home.HomeFragmentViewModel
import com.example.cocktails.ui.home.HomeFragmentViewModelFactory
import com.google.android.material.transition.MaterialContainerTransform
import kotlinx.coroutines.runBlocking

class DrinkDetailFragment : Fragment() {
    private lateinit var binding: FragmentDrinkDetailBinding
    private lateinit var viewModel: DrinkDetailFragmentViewModel
    private lateinit var homeViewModel: HomeFragmentViewModel
    private val args: DrinkDetailFragmentArgs by navArgs()
    private lateinit var drinks: List<Drink>

    override fun onCreate(savedInstanceState: Bundle?) {
        requireActivity().window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.STATUS_BAR_HIDDEN
        requireActivity().window.statusBarColor = TRANSPARENT
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            scrimColor = TRANSPARENT
        }
        setEnterSharedElementCallback(object : androidx.core.app.SharedElementCallback() {
            override fun onMapSharedElements(
                names: MutableList<String>?,
                sharedElements: MutableMap<String, View>?
            ) {
                super.onMapSharedElements(names, sharedElements)
                Log.d("TAG", "onMapSharedElements: $names and shared elements $sharedElements")
            }
        })
        viewModel = ViewModelProvider(
            this, DrinkDetailFragmentViewModelFactory(
                getRepository(requireContext()),
                requireContext()
            )
        ).get(DrinkDetailFragmentViewModel::class.java)
        homeViewModel = ViewModelProvider(
            this,
            HomeFragmentViewModelFactory(getRepository(requireContext()), requireContext())
        ).get(HomeFragmentViewModel::class.java)
        runBlocking {
            drinks = when (args.drinkMap.drinkType) {
                DrinkTypes.HOME.toString() -> viewModel.homeDrinks()
                DrinkTypes.COCKTAILS.toString() -> viewModel.cocktails()
                DrinkTypes.ORDINARY.toString() -> viewModel.ordinaryDrinks()
                else -> viewModel.drinks()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDrinkDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }
        binding.drinkPage.adapter = DrinkDetailAdapter(drinks, viewModel, this)
    }

    override fun onDetach() {
        super.onDetach()
        when (requireContext().resources.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_YES -> requireActivity().window.decorView.systemUiVisibility =
                View.STATUS_BAR_VISIBLE
            Configuration.UI_MODE_NIGHT_NO -> requireActivity().window.decorView.systemUiVisibility =
                View.STATUS_BAR_VISIBLE or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

}