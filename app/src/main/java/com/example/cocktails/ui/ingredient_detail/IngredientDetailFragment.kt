package com.example.cocktails.ui.ingredient_detail

import android.animation.PropertyValuesHolder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.transition.TransitionInflater
import com.example.cocktails.R
import com.example.cocktails.animatePropertyValuesHolder
import com.example.cocktails.databinding.FragmentIngredientDetailBinding
import com.example.cocktails.getRepository
import com.example.cocktails.loadUrl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class IngredientDetailFragment : Fragment() {
    private lateinit var binding: FragmentIngredientDetailBinding
    private lateinit var viewModel: IngredientDetailFragmentViewModel
    private val args: IngredientDetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val transition =
            TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
        sharedElementEnterTransition = transition
        viewModel = ViewModelProvider(
            requireActivity(),
            IngredientDetailFragmentViewModelFactory(getRepository(requireContext()))
        )
            .get(IngredientDetailFragmentViewModel::class.java)
        viewModel.ingredient = args.ingredient
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentIngredientDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            ingredientName.text = viewModel.ingredient.name
            ingredientThumb.loadUrl(viewModel.ingredient.thumb, requireContext())
            description.text = viewModel.ingredient.description
            drinks.alpha = 0F
            drinksLabel.alpha = 0F
            description.alpha = 0F
            descriptionLabel.alpha = 0F
            viewModel.ingredient.description?.let {
                descriptionLabel.text = getString(R.string.description)
                ingredientType.text = viewModel.ingredient.type
            }
            drinks.layoutManager = GridLayoutManager(requireContext(), 2)
        }

        viewModel.drinksWithIngredient().observe(requireActivity()) { drinkList ->
            binding.drinks.adapter = SmallDrinkAdapter(drinkList, requireContext())
        }

        lifecycleScope.launch(Dispatchers.Main) {
            delay(350)
            val fade = PropertyValuesHolder.ofFloat(View.ALPHA, 0F, 1F)
            val moveUp = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, 100F, 0F)
            val set = animatePropertyValuesHolder(
                listOf(
                    binding.descriptionLabel,
                    binding.description,
                    binding.drinks,
                    binding.drinksLabel
                ), fade, moveUp
            )
            set.start()
        }
    }
}