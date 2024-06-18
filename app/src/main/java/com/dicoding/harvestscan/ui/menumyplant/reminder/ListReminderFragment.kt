package com.dicoding.harvestscan.ui.menumyplant.reminder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.harvestscan.data.PlantRepository
import com.dicoding.harvestscan.data.local.room.PlantDatabase
import com.dicoding.harvestscan.databinding.FragmentListReminderBinding
import com.dicoding.harvestscan.ui.ViewModelFactory

class ListReminderFragment : Fragment() {
    private var _binding: FragmentListReminderBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ReminderViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListReminderBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val plantRepository = PlantRepository(PlantDatabase.getDatabase(requireContext()).HarvestScanDao())

        val adapter = ReminderListAdapter(plantRepository, viewModel)
        binding.rvReminders.adapter = adapter
        binding.rvReminders.layoutManager = LinearLayoutManager(requireContext())

        viewModel.getAllReminders().observe(viewLifecycleOwner) { reminders ->
            adapter.submitList(reminders)
        }
    }

}