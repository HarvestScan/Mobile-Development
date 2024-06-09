package com.dicoding.harvestscan.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.dicoding.harvestscan.R
import com.dicoding.harvestscan.databinding.FragmentHomeBinding
import com.dicoding.harvestscan.ui.MainViewModel
import com.dicoding.harvestscan.ui.ViewModelFactory
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel: MainViewModel by activityViewModels()
    private val viewModel: HomeViewModel by viewModels {
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.getSession().observe(viewLifecycleOwner) { user ->
            if (!user.isLogin) {
                val navController = findNavController()
                // Periksa apakah destinasi saat ini bukan LoginFragment
                if (navController.currentDestination?.id != R.id.navigation_login) {
                    navController.navigate(R.id.action_navigation_home_to_navigation_login)
                }
            }
        }

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.logout_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.menu_logout -> {
                        viewModel.logout()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        binding.about.setOnClickListener {
            AboutFragment().show(parentFragmentManager, "aboutTag")
        }
        binding.cardScan.setOnClickListener {
            mainViewModel.onScanButtonClicked()
        }
        binding.cardHistoryScan.setOnClickListener {
            mainViewModel.onHistoryButtonClicked()
        }
        binding.cardMyPlant.setOnClickListener {
            mainViewModel.onMyPlantButtonClicked()
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}