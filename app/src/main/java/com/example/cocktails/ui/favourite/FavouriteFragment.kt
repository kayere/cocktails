package com.example.cocktails.ui.favourite

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import androidx.core.animation.doOnEnd
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.cocktails.databinding.FragmentFavouriteBinding
import com.example.cocktails.getRepository
import com.google.android.material.transition.MaterialSharedAxis

class FavouriteFragment : Fragment() {

    private lateinit var binding: FragmentFavouriteBinding
    private lateinit var viewModel: FavouriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)
        viewModel =
            ViewModelProvider(this, FavouriteViewModelFactory(getRepository(requireContext()))).get(
                FavouriteViewModel::class.java
            )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.favouriteDrinks.observe(viewLifecycleOwner) { favouriteList ->
            if (favouriteList.isEmpty()) {
                binding.lottie.visibility = View.VISIBLE
                val fadeIn = PropertyValuesHolder.ofFloat(View.ALPHA, 1F)
                val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 0.5F, 1F)
                val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 0.5F, 1F)
                ObjectAnimator.ofPropertyValuesHolder(binding.notice, fadeIn, scaleX, scaleY)
                    .apply {
                        duration = 300L
                        startDelay = 4000L
                        interpolator = OvershootInterpolator()
                        doOnEnd { binding.notice.alpha = 1F }
                    }.start()
            }
        }
    }
}