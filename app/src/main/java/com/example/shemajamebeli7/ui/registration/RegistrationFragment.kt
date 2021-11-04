package com.example.shemajamebeli7.ui.registration


import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.shemajamebeli7.R
import com.example.shemajamebeli7.base.BaseFragment
import com.example.shemajamebeli7.extensions.checkEmail
import com.example.shemajamebeli7.databinding.RegistrationFragmentBinding
import com.example.shemajamebeli7.model.User
import com.example.shemajamebeli7.ui.viewmodel.AuthViewModel
import com.example.shemajamebeli7.utils.Resource

class RegistrationFragment :
    BaseFragment<RegistrationFragmentBinding>(RegistrationFragmentBinding::inflate) {

    private val viewModel by viewModels<AuthViewModel>()

    override fun init() {
        setListeners()
        setObservers()
    }

    private fun setListeners() {
        binding.registerBtn.isEnabled = false

        binding.emailField.editText?.doOnTextChanged { text, _, _, _ ->
            if (!text.toString().checkEmail()) {
                binding.emailField.error = getString(R.string.invalid_email)
                binding.registerBtn.isEnabled = false
            } else {
                binding.emailField.isErrorEnabled = false
                if (binding.passwordField.editText?.text.toString().isNotEmpty())
                    binding.registerBtn.isEnabled = true
            }
        }

        binding.passwordField.editText?.doOnTextChanged { text, _, _, _ ->
            if (text.toString()
                    .isEmpty() || text.toString() != binding.repeatPasswordField.editText?.text.toString()
            ) {
                binding.passwordField.error = getString(R.string.empty_password)
                binding.registerBtn.isEnabled = false
            } else {
                binding.passwordField.isErrorEnabled = false
                if (binding.emailField.editText?.text.toString().checkEmail())
                    binding.registerBtn.isEnabled = true
            }
        }

        binding.repeatPasswordField.editText?.doOnTextChanged { text, _, _, _ ->
            if (text.toString()
                    .isEmpty() || text.toString() != binding.passwordField.editText?.text.toString()
            ) {
                binding.passwordField.error = getString(R.string.repeat_password_pls)
                binding.registerBtn.isEnabled = false
            } else {
                binding.passwordField.isErrorEnabled = false
                if (binding.emailField.editText?.text.toString().checkEmail())
                    binding.registerBtn.isEnabled = true
            }
        }

        binding.registerBtn.setOnClickListener {
            val user = User(
                email = binding.emailField.editText?.text.toString(),
                password = binding.passwordField.editText?.text.toString()
            )
            viewModel.register(user = user)
        }
    }

    private fun setObservers() {
        viewModel.registerFinished.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    navigateToLogin()
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {

                }
            }
        }
    }

    private fun navigateToLogin() {
        val email = binding.emailField.editText?.text.toString()
        val password = binding.passwordField.editText?.text.toString()
        setFragmentResult("emailRequestKey", bundleOf("emailBundleKey" to email))
        setFragmentResult("passwordRequestKey", bundleOf("passwordBundleKey" to password))
        findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
    }

}