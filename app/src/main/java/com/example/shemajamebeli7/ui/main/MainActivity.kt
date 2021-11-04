package com.example.shemajamebeli7.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.shemajamebeli7.R
import com.example.shemajamebeli7.ui.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<AuthViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkLoggedInUser()
    }

    private fun checkLoggedInUser() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController: NavController = navHostFragment.navController
        lifecycleScope.launch {
            if (viewModel.readTokenFromDatastore() != "DefaultToken")
                navController.navigate(R.id.homeFragment)
        }
    }

}