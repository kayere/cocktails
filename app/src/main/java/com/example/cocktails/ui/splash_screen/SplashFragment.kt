package com.example.cocktails.ui.splash_screen

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.OvershootInterpolator
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.cocktails.Result
import com.example.cocktails.checkConnection
import com.example.cocktails.databinding.FragmentSplashBinding
import com.example.cocktails.getRepository
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding
    private lateinit var viewModel: SplashFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(
            requireActivity(), SplashFragmentViewModelFactory(
                getRepository(requireContext()), requireContext()
            )
        ).get(SplashFragmentViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.apply {
                if (checkFirstLaunch()) {
                    if (checkConnection(requireContext())) {
                        val fade = PropertyValuesHolder.ofFloat(View.ALPHA, 1F)
                        val moveUp = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, 100F, 0F)
                        ObjectAnimator.ofPropertyValuesHolder(binding.progressBar, fade, moveUp)
                            .apply {
                                startDelay = 300L
                            }.start()
                        val result = fetchFirstDrinks()
                        if (result == Result.PASS) {
                            checkDb()
                            findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToHomeFragment())
                        } else {
                            binding.progressBar.visibility = View.INVISIBLE
                            Snackbar.make(
                                requireContext(),
                                binding.root,
                                "Failed to load drinks. Please check your internet connection",
                                Snackbar.LENGTH_LONG
                            ).show()
                        }
                    } else {
                        val fadeIn = PropertyValuesHolder.ofFloat(View.ALPHA, 1F)
                        val fadeOut = PropertyValuesHolder.ofFloat(View.ALPHA, 0F)
                        val scaleXIn = PropertyValuesHolder.ofFloat(View.SCALE_X, 0F, 1F)
                        val scaleYIn = PropertyValuesHolder.ofFloat(View.SCALE_Y, 0F, 1F)
                        val scaleXOut = PropertyValuesHolder.ofFloat(View.SCALE_X, 1F, 0F)
                        val scaleYOut = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1F, 0F)

                        ObjectAnimator.ofPropertyValuesHolder(
                            binding.connectionAlert,
                            fadeIn,
                            scaleXIn,
                            scaleYIn
                        ).apply {
                            interpolator = OvershootInterpolator()
                            startDelay = 300L
                        }.start()

                        binding.cancelAlert.setOnClickListener {
                            ObjectAnimator.ofPropertyValuesHolder(binding.connectionAlert, fadeOut, scaleXOut, scaleYOut).apply {
                                interpolator = OvershootInterpolator()
                            }.start()
                        }
                    }
                } else {
                    binding.progressBar.visibility = View.INVISIBLE
                    checkDb()
                    findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToHomeFragment())
                }

            }
        }
    }
}