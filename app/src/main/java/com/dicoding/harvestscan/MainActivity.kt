package com.dicoding.harvestscan

import android.animation.ObjectAnimator
import android.graphics.drawable.AnimationDrawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
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
import com.google.android.material.card.MaterialCardView

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

        setupView()

        // Memulai animasi gradasi di card view with id "about"
        startGradientAnimation()

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        homeViewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                // Navigate to LoginFragment if not logged in
                if (navController.currentDestination?.id != R.id.navigation_login) {
                    navController.navigate(R.id.navigation_login)
                }
                // Hide BottomNavigationView
                binding.navView.visibility = View.GONE
                binding.navHostFragmentMain.visibility = View.GONE
                return@observe
            }

            // Setup navigation only if user is logged in
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

    private fun startGradientAnimation() {
        val aboutCardView: MaterialCardView = findViewById(R.id.about)
        aboutCardView.setBackgroundResource(R.drawable.gradient_animation)
        val animationDrawable = aboutCardView.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(1000)  // Durasi fade-in lebih cepat
        animationDrawable.setExitFadeDuration(1000)   // Durasi fade-out lebih cepat
        animationDrawable.start()
    }

    private fun setupNavigation(navController: NavController) {
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val indicator: View = findViewById(R.id.nav_host_fragment_main)

        navView.setupWithNavController(navController)

        // Set initial position of the indicator
        navView.post {
            moveIndicator(indicator, navView, R.id.navigation_home)
        }

        navView.setOnItemSelectedListener { item ->
            moveIndicator(indicator, navView, item.itemId)
            navController.navigate(item.itemId)
            true
        }

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

        mainViewModel.navigateToAddReminder.observe(this) { navigate ->
            if (navigate) {
                navView.selectedItemId = R.id.navigation_my_plant
                navController.navigate(R.id.action_navigation_my_plant_to_navigation_reminder)
                mainViewModel.onNavigatedToAddReminder()
            }
        }
    }

    private fun moveIndicator(indicator: View, bottomNavigationView: BottomNavigationView, itemId: Int) {
        val menuView = bottomNavigationView.getChildAt(0) as ViewGroup
        val targetView = menuView.findViewById<View>(itemId)

        targetView?.let {
            // Convert 5dp to pixels
            val extraMargin = resources.getDimensionPixelSize(R.dimen.extra_margin)

            // Calculate the target X position with additional margin
            val targetX = it.x + it.width / 2 - indicator.width / 2 + extraMargin
            ObjectAnimator.ofFloat(indicator, "x", targetX).apply {
                duration = 300
                start()
            }
        }
    }

    private fun setupView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
