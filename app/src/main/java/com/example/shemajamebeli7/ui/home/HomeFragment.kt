package com.example.shemajamebeli7.ui.home

import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.shemajamebeli7.R
import com.example.shemajamebeli7.base.BaseFragment
import com.example.shemajamebeli7.databinding.FragmentHomeBinding
import com.example.shemajamebeli7.datastore.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    override fun init() {
        setListeners()
        setObservers()
    }

    private fun setObservers() {
        SessionManager.readEmailFromDataStore(context = requireContext())
            .observe(viewLifecycleOwner, { email ->
                binding.emailTxtView.text = email
            })
    }

    private fun setListeners() {
        binding.logOutBtn.setOnClickListener {
            removeTokenFromDataStore()
            findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
        }
    }

    private fun removeTokenFromDataStore() {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            SessionManager.removeFromDataStore(context = requireContext())
        }
    }

}