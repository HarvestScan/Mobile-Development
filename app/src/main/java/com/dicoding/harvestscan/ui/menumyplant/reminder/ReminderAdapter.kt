package com.dicoding.harvestscan.ui.menumyplant.reminder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.harvestscan.R
import com.dicoding.harvestscan.data.PlantRepository
import com.dicoding.harvestscan.data.local.room.Reminder

class ReminderListAdapter(private val plantRepository: PlantRepository) : ListAdapter<Reminder, ReminderListAdapter.ReminderViewHolder>(ReminderDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_reminder, parent, false)
        return ReminderViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReminderViewHolder, position: Int) {
        val reminder = getItem(position)
        holder.bind(reminder)
    }

    inner class ReminderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvDesc: TextView = itemView.findViewById(R.id.tv_desc_reminder)
        private val tvPlantName: TextView = itemView.findViewById(R.id.tv_plant_name)
        private val tvDays: TextView = itemView.findViewById(R.id.tv_days)
        private val btnDelete: ImageButton = itemView.findViewById(R.id.btn_delete)

        fun bind(reminder: Reminder) {
            tvDesc.text = reminder.notes
            tvDays.text = reminder.daysOfWeek

            plantRepository.getPlantById(reminder.plantId).observe(itemView.context as LifecycleOwner) { plant ->
                tvPlantName.text = plant?.name ?: "Unknown Plant"
            }

            btnDelete.setOnClickListener {
                // Implementasi aksi hapus reminder jika diperlukan
            }
        }
    }

    class ReminderDiffCallback : DiffUtil.ItemCallback<Reminder>() {
        override fun areItemsTheSame(oldItem: Reminder, newItem: Reminder): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Reminder, newItem: Reminder): Boolean {
            return oldItem == newItem
        }
    }
}