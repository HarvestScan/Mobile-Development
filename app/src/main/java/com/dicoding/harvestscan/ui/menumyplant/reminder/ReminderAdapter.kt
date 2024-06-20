package com.dicoding.harvestscan.ui.menumyplant.reminder

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
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
import com.dicoding.harvestscan.data.local.room.Plant
import com.dicoding.harvestscan.data.local.room.Reminder

class ReminderListAdapter(
    private val plantRepository: PlantRepository,
    private val reminderViewModel: ReminderViewModel
) : ListAdapter<Reminder, ReminderListAdapter.ReminderViewHolder>(ReminderDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_reminder, parent, false)
        return ReminderViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReminderViewHolder, position: Int) {
        val reminder = getItem(position)
        holder.bind(reminder, holder.itemView.context)
    }

    inner class ReminderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvDesc: TextView = itemView.findViewById(R.id.tv_desc_reminder)
        private val tvPlantName: TextView = itemView.findViewById(R.id.tv_plant_name)
        private val tvDays: TextView = itemView.findViewById(R.id.tv_days)
        private val btnDelete: ImageButton = itemView.findViewById(R.id.btn_delete)
        private lateinit var plantList: List<Plant>

        fun bind(reminder: Reminder, context: Context) {
            tvDesc.text = reminder.notes
            tvDays.text = reminder.daysOfWeek

            plantRepository.getPlantById(reminder.plantId).observe(itemView.context as LifecycleOwner) { plant ->
                tvPlantName.text = plant?.name ?: "Unknown Plant"
            }

            btnDelete.setOnClickListener {
                reminderViewModel.deleteReminderById(reminder.id)
                cancelAlarm(context, reminder)
                notifyItemRemoved(adapterPosition)
            }
        }
        private fun cancelAlarm(context:Context, reminder: Reminder) {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val selectedPlant = plantList.find { it.id == reminder.plantId }
            val plantName = selectedPlant?.name

            // Split daysOfWeek to get individual days
            reminder.daysOfWeek.split(", ").forEach { day ->
                // Create intent for the ReminderReceiver
                val intent = Intent(context, ReminderReceiver::class.java).apply {
                    putExtra(context.getString(R.string.plantname), plantName)
                    putExtra(context.getString(R.string.notes), reminder.notes)
                }

                // Create PendingIntent with the same requestCode as when setting the alarm
                val pendingIntent = PendingIntent.getBroadcast(
                    context,
                    day.hashCode(), // Ensure requestCode is unique for each day
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )

                // Cancel the alarm using AlarmManager
                alarmManager.cancel(pendingIntent)
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