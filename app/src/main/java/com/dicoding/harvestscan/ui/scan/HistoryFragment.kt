package com.dicoding.harvestscan.ui.scan

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.harvestscan.data.local.room.ScanHistory
import com.dicoding.harvestscan.databinding.FragmentHistoryBinding
import com.dicoding.harvestscan.ui.ViewModelFactory
import java.io.File

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ScanViewModel by viewModels {
        ViewModelFactory.getInstance(requireActivity())
    }

    private lateinit var adapter: HistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = HistoryAdapter(emptyList(), this::onHistoryItemClicked, this::onDeleteHistoryClicked)

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvHeroes.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.rvHeroes.addItemDecoration(itemDecoration)
        binding.rvHeroes.adapter = adapter

        viewModel.historyList.observe(viewLifecycleOwner) { historyList ->
            adapter.updateHistoryList(historyList)
            toggleEmptyState(historyList.isEmpty())
        }
    }

    private fun toggleEmptyState(isEmpty: Boolean) {
        if (isEmpty) {
            binding.tvNoHistory.visibility = View.VISIBLE
            binding.rvHeroes.visibility = View.GONE
        } else {
            binding.tvNoHistory.visibility = View.GONE
            binding.rvHeroes.visibility = View.VISIBLE
        }
    }

    private fun onHistoryItemClicked(scanHistory: ScanHistory) {
        val action = HistoryFragmentDirections.actionNavigationHistoryToNavigationResult()
        action.imageUri = scanHistory.imageUri
        action.resultLabel = scanHistory.label
        action.resultScore = scanHistory.confidenceScore
        action.resultDescription = scanHistory.description
        action.resultTips = scanHistory.tips

        findNavController().navigate(action)
    }

    private fun onDeleteHistoryClicked(scanHistory: ScanHistory) {
        viewModel.deleteHistory(scanHistory)
        deleteImageFromInternalStorage(scanHistory.imageUri)
    }

    private fun deleteImageFromInternalStorage(imageUri: String) {
        val file = File(Uri.parse(imageUri).path ?: return)
        if (file.exists()) {
            file.delete()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
