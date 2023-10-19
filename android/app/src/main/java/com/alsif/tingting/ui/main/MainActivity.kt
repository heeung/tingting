package com.alsif.tingting.ui.main

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.alsif.tingting.R
import com.alsif.tingting.base.BaseActivity
import com.alsif.tingting.databinding.ActivityMainBinding
import com.alsif.tingting.ui.login.LoginModalBottomSheet
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    lateinit var bottomNavigationView: BottomNavigationView

    private val sharedViewModel: MainActivityViewModel by viewModels()

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

    fun loginModalBottomSheet() {
        val modal = LoginModalBottomSheet()
        modal.show(supportFragmentManager, LoginModalBottomSheet.TAG)
    }

    // boolean을 리턴함으로써 확장성 추가
    fun requireLogin() : Boolean {
        return when(sharedViewModel.getAccessToken()) {
            null -> {
                //  TODO api를 호출 하려나??
                navController.popBackStack()
                loginModalBottomSheet()
                false
            }

            else -> {
                true
            }
        }
    }
}