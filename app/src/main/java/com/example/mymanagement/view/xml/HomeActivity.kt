package com.example.mymanagement.view.xml

import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.mymanagement.R
import com.example.mymanagement.databinding.ActivityHomeBinding
import com.example.mymanagement.view.base.BindingActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeActivity : BindingActivity<ActivityHomeBinding>(R.layout.activity_home) {
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navController =
            (supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment).navController
        binding.navView.setupWithNavController(navController)

        lifecycleScope.launch {
            navController.currentBackStackEntryFlow.collect {
                binding.navView.isVisible = it.destination.label == "HomeFragment" ||
                        it.destination.label == "TransportationFragment" ||
                        it.destination.label == "ScheduleFragment" ||
                        it.destination.label == "OtherFragment"
            }
        }
    }
}