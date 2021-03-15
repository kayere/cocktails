package com.example.cocktails.ui.splash_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.cocktails.databinding.FragmentSplashBinding
import com.example.cocktails.getRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding
    private lateinit var viewModel: SplashActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(
            requireActivity(), SplashActivityViewModelFactory(
                getRepository(requireContext()), requireContext()
            )
        ).get(SplashActivityViewModel::class.java)
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
            viewModel.checkDb()
            delay(1000)
            findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToHomeFragment())
        }
    }
}