package com.example.cocktails.ui.drinks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.cocktails.DrinkTypes
import com.example.cocktails.data.models.Drink
import com.example.cocktails.databinding.FragmentDrinksBinding
import com.example.cocktails.getRepository
import com.google.android.material.transition.MaterialContainerTransform
import kotlinx.coroutines.runBlocking

class DrinksFragment : Fragment() {
    private lateinit var binding: FragmentDrinksBinding
    private lateinit var drinks: List<Drink>
    private lateinit var title: String
    private val args: DrinksFragmentArgs by navArgs()
    private lateinit var viewModel: DrinksFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedElementEnterTransition = MaterialContainerTransform()

        viewModel = ViewModelProvider(
            this,
            DrinksFragmentViewModelFactory(getRepository(requireContext()), requireContext())
        ).get(DrinksFragmentViewModel::class.java)
        runBlocking {
            drinks = when (args.drinksType) {
                DrinkTypes.HOME.toString() -> viewModel.homeDrinks()
                DrinkTypes.COCKTAILS.toString() -> viewModel.cocktails()
                DrinkTypes.ORDINARY.toString() -> viewModel.ordinaryDrinks()
                @Suppress("UNCHECKED_CAST")
                DrinkTypes.FAVOURITES.toString() -> viewModel.favouriteDrinks() as List<Drink>
                else -> viewModel.drinks()
            }
        }
        title = when (args.drinksType) {
            DrinkTypes.HOME.toString() -> "Home"
            DrinkTypes.COCKTAILS.toString() -> "Cocktails"
            DrinkTypes.ORDINARY.toString() -> "Ordinary drinks"
            DrinkTypes.FAVOURITES.toString() -> "Favourites"
            else -> "All drinks"
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDrinksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.title.text = title
        binding.drinkList.adapter = DrinksAdapter(drinks, this, viewModel)
    }

}