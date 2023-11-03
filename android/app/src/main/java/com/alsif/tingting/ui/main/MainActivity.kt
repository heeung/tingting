package com.alsif.tingting.ui.main

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.navigation.ui.setupWithNavController
import com.alsif.tingting.R
import com.alsif.tingting.base.BaseActivity
import com.alsif.tingting.databinding.ActivityMainBinding
import com.alsif.tingting.ui.home.HomeFragmentDirections
import com.alsif.tingting.ui.login.LoginModalBottomSheet
import com.alsif.tingting.util.extension.setStatusBarOrigin
import com.alsif.tingting.util.extension.setStatusBarTransparent
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val TAG = "MainActivity"
@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var loginBottomSheet: LoginModalBottomSheet

    private val sharedViewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initNavController()
        initFragmentChangedListener()
        setBottomNavigationView()
        subscribe()
        setClickListeners()
    }

    private fun setClickListeners() {
        binding.layoutSearchBar.setOnClickListener {
            navController.navigate(R.id.searchFragment)
        }
        binding.edittextSearch.setOnClickListener {
            navController.navigate(R.id.searchFragment)
        }
    }

    private fun setBottomNavigationView() {
        bottomNavigationView = binding.bottomNavigation
        bottomNavigationView.setupWithNavController(navController)

        // bottomNav에 있는 아이템에 강제로 navigate시 다시 클릭이 안되는 문제 해결 코드..
        bottomNavigationView.setOnItemSelectedListener {
            navController.navigate(it.itemId, args = null, navOptions {
                launchSingleTop = true
                popUpTo(it.itemId)
            })
            true
        }
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

    private fun initFragmentChangedListener() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.searchFragment -> {
                    binding.layoutAppbar.visibility = View.GONE
                }
                R.id.concertDetailFragment -> {
                    binding.layoutAppbar.visibility = View.GONE
                }
                else -> {
                    binding.layoutAppbar.visibility = View.VISIBLE
                }
            }
        }
    }

    fun showLoginBottomSheet() {
        loginBottomSheet = LoginModalBottomSheet()
        loginBottomSheet.show(supportFragmentManager, LoginModalBottomSheet.TAG)
        subscribeLogin()
    }

    fun dismissLoginBottomSheet() {
        loginBottomSheet.dismiss()
    }

    fun subscribeLogin() {
        lifecycleScope.launch {
            sharedViewModel.isLogined.collectLatest {
                if (it) {
                    Log.d(TAG, "subscribe: isLogined가 true로 바뀌었습니다.")
                    dismissLoginBottomSheet()
                }
            }
        }
    }

    fun subscribe() {
        lifecycleScope.launch {
            sharedViewModel.isLogined.collectLatest {
                if (!it) {
                    navController.navigate(R.id.homeFragment)
                }
            }
        }
    }

    // boolean을 리턴함으로써 확장성 추가
    fun requireLogin() : Boolean {
        return when(sharedViewModel.getAccessToken()) {
            null -> {
                //  TODO api를 호출 하려나??
                navController.popBackStack()
                showLoginBottomSheet()
                Log.d(TAG, "requireLogin: AccessToken 비어있습니다 -> ${sharedViewModel.getAccessToken()}")
                false
            }

            else -> {
                Log.d(TAG, "requireLogin: AccessToken 있습니다. -> ${sharedViewModel.getAccessToken()}")
                true
            }
        }
    }

    fun logout() {
        UserApiClient.instance.logout {
            sharedViewModel.resetToken()
            showToast(LOGOUT_MESSAGE)
            // TODO 홈으로 가기
        }
    }

    companion object {
        private const val LOGOUT_MESSAGE = "로그아웃 되었습니다."
    }
}