package com.dicoding.harvestscan.ui.scan

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.harvestscan.data.local.room.ScanHistory
import com.dicoding.harvestscan.databinding.ItemHistoryBinding

class HistoryAdapter(
    var historyList: List<ScanHistory>,
    private val onItemClicked: (ScanHistory) -> Unit,
    private val onDeleteClicked: (ScanHistory) -> Unit
) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val historyItem = historyList[position]
        holder.bind(historyItem, onItemClicked, onDeleteClicked)
    }

    override fun getItemCount(): Int = historyList.size

    fun updateHistoryList(newHistoryList: List<ScanHistory>) {
        val diffCallback = HistoryDiffCallback(historyList, newHistoryList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        historyList = newHistoryList
        diffResult.dispatchUpdatesTo(this)
    }

    inner class HistoryViewHolder(internal val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(scanHistory: ScanHistory, onItemClicked: (ScanHistory) -> Unit, onDeleteClicked: (ScanHistory) -> Unit) {
            binding.imageHistoryScan.setImageURI(Uri.parse(scanHistory.imageUri))
            binding.tvLabelHistory.text = scanHistory.label
            binding.tvScoreHistory.text = scanHistory.confidenceScore
            binding.root.setOnClickListener {
                onItemClicked(scanHistory)
            }
            binding.btnDelete.setOnClickListener {
                onDeleteClicked(scanHistory)
            }
        }
    }

    class HistoryDiffCallback(
        private val oldList: List<ScanHistory>,
        private val newList: List<ScanHistory>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}
