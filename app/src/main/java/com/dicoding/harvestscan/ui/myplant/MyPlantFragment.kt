package com.dicoding.harvestscan.ui.myplant

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.harvestscan.R
import com.dicoding.harvestscan.data.local.room.Plant
import com.dicoding.harvestscan.databinding.FragmentMyplantBinding
import com.dicoding.harvestscan.ui.ViewModelFactory

class MyPlantFragment : Fragment() {

    private var _binding: FragmentMyplantBinding? = null
    private val binding get() = _binding!!

    private lateinit var plantAdapter: PlantAdapter
    private val plantViewModel: MyPlantViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyplantBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        binding.btnAddPlant.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_my_plant_to_navigation_add_plant)
        }
    }

    private fun setupRecyclerView() {
        plantAdapter = PlantAdapter(
            onAddReminderClick = { plant ->
                navigateToAddReminder(plant)
            },
            onDeletePlantClick = { plant ->
                plantViewModel.delete(plant)
            }
        )

        binding.recyclerViewPlants.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = plantAdapter
        }

        plantViewModel.allPlants.observe(viewLifecycleOwner) { plants ->
            plants?.let { plantAdapter.submitList(it) }
        }
    }

    private fun navigateToAddReminder(plant: Plant) {
        val action = MyPlantFragmentDirections.actionNavigationMyPlantToNavigationReminder()
        action.plantId = plant.id
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
