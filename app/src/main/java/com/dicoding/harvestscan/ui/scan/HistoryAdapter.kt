package com.dicoding.harvestscan.ui.scan

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.harvestscan.data.local.room.ScanHistory
import com.dicoding.harvestscan.databinding.ItemHistoryBinding

class HistoryAdapter(
    var historyList: List<ScanHistory>,
    private val onItemClicked: (ScanHistory) -> Unit
) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val historyItem = historyList[position]
        holder.bind(historyItem, onItemClicked)
    }

    override fun getItemCount(): Int = historyList.size

    class HistoryViewHolder(private val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(scanHistory: ScanHistory, onItemClicked: (ScanHistory) -> Unit) {
            binding.tvLabelHistory.text = scanHistory.label
            binding.tvScoreHistory.text = scanHistory.confidenceScore.toString()
            // Optionally set the image using an image loading library like Glide or Picasso
            binding.root.setOnClickListener {
                onItemClicked(scanHistory)
            }
        }
    }
}