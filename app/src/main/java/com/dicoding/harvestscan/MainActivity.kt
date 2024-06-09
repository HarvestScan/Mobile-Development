package com.dicoding.harvestscan

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
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

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        homeViewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                // Navigasi ke LoginFragment jika belum login
                if (navController.currentDestination?.id != R.id.navigation_login) {
                    navController.navigate(R.id.navigation_login)
                }
                // Sembunyikan BottomNavigationView
                binding.navView.visibility = View.GONE
                return@observe
            }

            // Hanya setup navigation jika user sudah login
            setupNavigation(navController)
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_login, R.id.navigation_register -> {
                    binding.navView.visibility = View.GONE
                }
                else -> {
                    binding.navView.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setupNavigation(navController: NavController) {
        val navView: BottomNavigationView = binding.navView

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

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
