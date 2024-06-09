package com.dicoding.harvestscan

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.dicoding.harvestscan.databinding.ActivityMainBinding
import com.dicoding.harvestscan.ui.MainViewModel
import com.dicoding.harvestscan.ui.ViewModelFactory
import com.dicoding.harvestscan.ui.home.HomeViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.navController

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.nav_view)
        val indicator: View = findViewById(R.id.nav_host_fragment_main)

//        val navController = findNavController(R.id.nav_host_fragment_activity_main)
//        bottomNavigationView.setupWithNavController(navController)

        // Set initial position of the indicator
        bottomNavigationView.post {
            moveIndicator(indicator, bottomNavigationView, bottomNavigationView.selectedItemId)
        }

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            moveIndicator(indicator, bottomNavigationView, item.itemId)
            navController.navigate(item.itemId)
            true
        }

        homeViewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                // Navigasi ke LoginFragment jika belum login
                if (navController.currentDestination?.id != R.id.navigation_login) {
                    navController.navigate(R.id.navigation_login)
                }
                // Sembunyikan BottomNavigationView
                bottomNavigationView.visibility = View.GONE
                return@observe
            }

            // Hanya setup navigation jika user sudah login
            setupNavigation(navController)
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_login, R.id.navigation_register -> {
                    bottomNavigationView.visibility = View.GONE
                }
                else -> {
                    bottomNavigationView.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setupNavigation(navController: NavController) {
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        navView.setupWithNavController(navController)

        mainViewModel.navigateToScan.observe(this) { navigate ->
            if (navigate) {
                navView.selectedItemId = R.id.navigation_scan
                mainViewModel.onNavigatedToScan()
            }
        }

        mainViewModel.navigateToHistory.observe(this) { navigate ->
            if (navigate) {
                navView.selectedItemId = R.id.navigation_scan
                navController.navigate(R.id.action_navigation_scan_to_navigation_history)
                mainViewModel.onNavigatedToHistory()
            }
        }

        mainViewModel.navigateToMyPlant.observe(this) { navigate ->
            if (navigate) {
                navView.selectedItemId = R.id.navigation_my_plant
                mainViewModel.onNavigatedToMyPlant()
            }
        }
    }

    private fun moveIndicator(indicator: View, bottomNavigationView: BottomNavigationView, itemId: Int) {
        val menuView = bottomNavigationView.getChildAt(0) as ViewGroup
        var targetView: View? = null

        menuView.forEach { view ->
            if (view.id == itemId) {
                targetView = view
            }
        }

        targetView?.let {
            val targetX = it.x + it.width / 2 - indicator.width / 2
            ObjectAnimator.ofFloat(indicator, "x", targetX).apply {
                duration = 300
                start()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
