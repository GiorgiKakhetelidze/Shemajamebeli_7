package com.example.shemajamebeli7.ui.home

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.shemajamebeli7.R
import com.example.shemajamebeli7.base.BaseFragment
import com.example.shemajamebeli7.databinding.FragmentHomeBinding
import com.example.shemajamebeli7.ui.viewmodel.AuthViewModel
import kotlinx.coroutines.launch


class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel by activityViewModels<AuthViewModel>()

    override fun init() {
        setListeners()
        setEmail()
    }

    private fun setEmail() {
        viewLifecycleOwner.lifecycleScope.launch {
           binding.emailTxtView.text = viewModel.readEmailFromDataStore()
        }
    }

    private fun setListeners() {
        binding.logOutBtn.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch{
                viewModel.removeTokenFromDataStore()
            }

            findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
        }
    }

}