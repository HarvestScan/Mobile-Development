package com.dicoding.harvestscan.ui.myplant//package com.dicoding.harvestscan.ui.myplant

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.harvestscan.R
import com.dicoding.harvestscan.databinding.FragmentMyplantBinding

class MyPlantFragment : Fragment() {

    private var _binding: FragmentMyplantBinding? = null
    private val binding get() = _binding!!

    private lateinit var plantAdapter: PlantAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyplantBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup segmented button
        binding.segmentedButton.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.btn_my_garden -> {
                        // Handle My Garden button click
                        // Toast.makeText(requireContext(), "My Garden selected", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_navigation_my_plant_to_navigation_add_plant)
                    }
                    R.id.btn_reminder -> {
                        // Navigate to ReminderFragment
                        findNavController().navigate(R.id.action_navigation_my_plant_to_navigation_reminder)
                    }
                }
            }
        }

        // Setup Add Plant button
        binding.btnAddPlant.setOnClickListener {
            // Handle Add Plant button click
            // Toast.makeText(requireContext(), "Add Plant Clicked!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_navigation_my_plant_to_navigation_add_plant)
            // Navigate to Add Plant page (to be implemented)
        }

        // Setup RecyclerView
        plantAdapter = PlantAdapter {
            // Handle Add Reminder button click for a specific plant
            findNavController().navigate(R.id.action_navigation_my_plant_to_navigation_reminder)
        }
        binding.recyclerViewPlants.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = plantAdapter
        }

        // Load plants into RecyclerView (this is just a placeholder, implement actual data loading)
        loadPlants()
    }

    private fun loadPlants() {
        val plants = listOf(
            Plant("Rose", "Flower", "Rosa", "https://example.com/rose.jpg"),
            Plant("Tulip", "Flower", "Tulipa", "https://example.com/tulip.jpg"),
            Plant("Sunflower", "Flower", "Helianthus", "https://example.com/sunflower.jpg")
        )
        plantAdapter.submitList(plants)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
