package com.dicoding.harvestscan.ui.scan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.harvestscan.R
import com.dicoding.harvestscan.databinding.FragmentHistoryBinding
import com.dicoding.harvestscan.ui.ViewModelFactory

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ScanViewModel by viewModels {
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = HistoryAdapter(emptyList()) { scanHistory ->
            val action = HistoryFragmentDirections.actionNavigationHistoryToNavigationResult()
            action.imageUri = scanHistory.imageUri
            action.resultLabel = scanHistory.label
            action.resultScore = scanHistory.confidenceScore.toString()
            action.resultDescription = scanHistory.description
            action.resultTips = scanHistory.tips

            findNavController().navigate(action)
        }
        binding.rvHeroes.layoutManager = LinearLayoutManager(context)
        binding.rvHeroes.adapter = adapter

        viewModel.historyList.observe(viewLifecycleOwner) { historyList ->
            adapter.historyList = historyList
            adapter.notifyDataSetChanged()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}