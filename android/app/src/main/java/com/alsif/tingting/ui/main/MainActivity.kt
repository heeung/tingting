package com.alsif.tingting.ui.main

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.alsif.tingting.R
import com.alsif.tingting.base.BaseActivity
import com.alsif.tingting.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initNavController()
        setBottomNavigationView()
    }

    private fun setBottomNavigationView(){
        bottomNavigationView = binding.bottomNavigation
        bottomNavigationView.setupWithNavController(navController)
    }

    private fun initNavController() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHostFragment.navController

        val inflater = navHostFragment.navController.navInflater
        val graph = inflater.inflate(R.navigation.nav_graph)

        graph.setStartDestination(R.id.homeFragment)

        val navController = navHostFragment.navController
        navController.setGraph(graph, intent.extras)
    }
}