package com.dicoding.harvestscan.ui.myplant

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.harvestscan.R
import com.dicoding.harvestscan.data.local.room.Plant

class PlantAdapter(private val onAddReminderClick: (Plant) -> Unit) :
    ListAdapter<Plant, PlantAdapter.PlantViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_plant, parent, false)
        return PlantViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        val plant = getItem(position)
        holder.bind(plant)
    }

    inner class PlantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val plantName: TextView = itemView.findViewById(R.id.plantName)
        private val plantType: TextView = itemView.findViewById(R.id.plantType)
        private val botanicalName: TextView = itemView.findViewById(R.id.botanicalName)
        private val plantImage: ImageView = itemView.findViewById(R.id.plantImage)
        private val addReminderButton: Button = itemView.findViewById(R.id.addReminderButton)
        private val editPlantButton: ImageView = itemView.findViewById(R.id.editPlant)
        private val deletePlantButton: ImageView = itemView.findViewById(R.id.deletePlant)

        init {
            editPlantButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val plant = getItem(position)
                    // Tambahkan kode untuk memulai aktivitas edit di sini
                    // Misalnya: listener.onEditClicked(plant)
                }
            }

            deletePlantButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val plant = getItem(position)
                    // Tambahkan kode untuk menampilkan dialog konfirmasi delete di sini
                    // Misalnya: listener.onDeleteClicked(plant)
                }
            }
        }

        fun bind(plant: Plant) {
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
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Plant, newItem: Plant): Boolean {
            return oldItem == newItem
        }
    }
}
