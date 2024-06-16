package com.dicoding.harvestscan.ui.myplant

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.harvestscan.R
import com.dicoding.harvestscan.data.local.room.Plant

class PlantAdapter(
    private val onAddReminderClick: (Plant) -> Unit,
    private val onDeletePlantClick: (Plant) -> Unit
) : ListAdapter<Plant, PlantAdapter.PlantViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_plant, parent, false)
        return PlantViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        val plant = getItem(position)
        holder.bind(plant, onAddReminderClick)
    }

    inner class PlantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val plantName: TextView = itemView.findViewById(R.id.plantName)
        private val plantType: TextView = itemView.findViewById(R.id.plantType)
        private val botanicalName: TextView = itemView.findViewById(R.id.botanicalName)
        private val plantImage: ImageView = itemView.findViewById(R.id.plantImage)
        private val addReminderButton: Button = itemView.findViewById(R.id.addReminderButton)
        private val deletePlantButton: ImageView = itemView.findViewById(R.id.deletePlant)

        init {
            deletePlantButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val plant = getItem(position)
                    showDeleteDialog(plant)
                }
            }
        }

        private fun showDeleteDialog(plant: Plant) {
            val context = itemView.context
            val alertDialog = AlertDialog.Builder(context)
                .setMessage(context.getString(R.string.are_you_sure_you_want_to_delete_this_plant))
                .setPositiveButton(context.getString(R.string.yes)) { dialog, which ->
                    onDeletePlantClick(plant)
                    Toast.makeText(itemView.context,
                        context.getString(R.string.plant_deleted), Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton(context.getString(R.string.no), null)
                .create()

            alertDialog.setOnShowListener {
                val messageTextView = alertDialog.findViewById<TextView>(android.R.id.message)
                messageTextView?.setTextColor(ContextCompat.getColor(itemView.context, R.color.black))

                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)?.setTextColor(ContextCompat.getColor(itemView.context, R.color.black))
                alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)?.setTextColor(ContextCompat.getColor(itemView.context, R.color.black))
            }

            alertDialog.show()
        }

        fun bind(plant: Plant, onAddReminderClick: (Plant) -> Unit) {
            plantName.text = plant.name
            plantType.text = plant.type
            botanicalName.text = plant.botanicalName
            Glide.with(itemView.context).load(plant.imageUri).into(plantImage)

            addReminderButton.setOnClickListener {
                onAddReminderClick(plant)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Plant>() {
        override fun areItemsTheSame(oldItem: Plant, newItem: Plant): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Plant, newItem: Plant): Boolean {
            return oldItem == newItem
        }
    }
}
