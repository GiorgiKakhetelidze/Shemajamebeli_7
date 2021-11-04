package com.example.shemajamebeli7.ui.login

import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.shemajamebeli7.R
import com.example.shemajamebeli7.base.BaseFragment
import com.example.shemajamebeli7.extensions.checkEmail
import com.example.shemajamebeli7.databinding.LoginFragmentBinding
import com.example.shemajamebeli7.model.User
import com.example.shemajamebeli7.ui.viewmodel.AuthViewModel
import com.example.shemajamebeli7.utils.Resource

class LoginFragment : BaseFragment<LoginFragmentBinding>(LoginFragmentBinding::inflate) {

    private val viewModel by viewModels<AuthViewModel>()

    override fun init() {
        setListeners()
        setObservers()
        setData()
    }

    private fun setData(){
        setFragmentResultListener("emailRequestKey") { _, bundle ->
            val email = bundle.getString("emailBundleKey")!!
            binding.emailField.editText?.setText(email)
        }
        setFragmentResultListener("passwordRequestKey") { _, bundle ->
            val pass = bundle.getString("passwordBundleKey")
            binding.passwordField.editText?.setText(pass.toString())
        }
    }

    private fun setListeners() {
        binding.loginBtn.isEnabled = false

        binding.emailField.editText?.doOnTextChanged { text, _, _, _ ->
            if (!text.toString().checkEmail()) {
                binding.emailField.error = getString(R.string.invalid_email)
                binding.loginBtn.isEnabled = false
            } else {
                binding.emailField.isErrorEnabled = false
                if (binding.passwordField.editText?.text.toString().isNotEmpty())
                    binding.loginBtn.isEnabled = true
            }
        }

        binding.passwordField.editText?.doOnTextChanged { text, _, _, _ ->
            if (text.toString().isEmpty()) {
                binding.passwordField.error = getString(R.string.empty_password)
                binding.loginBtn.isEnabled = false
            } else {
                binding.passwordField.isErrorEnabled = false
                if (binding.emailField.editText?.text.toString().checkEmail())
                    binding.loginBtn.isEnabled = true
            }
        }

        binding.loginBtn.setOnClickListener {
            val user = User(
                email = binding.emailField.editText?.text.toString(),
                password = binding.passwordField.editText?.text.toString()
            )
            viewModel.login(user = user)
        }

        binding.registerBtn.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
        }

    }

    private fun setObservers() {
        viewModel.loginFinished.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    val token = resource.data?.token
                    val email = binding.emailField.editText?.text.toString()
                    val isChecked = binding.checkBoxView.isChecked
                    viewModel.setSession(token!!, email,isChecked)
                    navigateToHomePage()
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {

                }
            }
        }

    }

    private fun navigateToHomePage() {
        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
    }

}