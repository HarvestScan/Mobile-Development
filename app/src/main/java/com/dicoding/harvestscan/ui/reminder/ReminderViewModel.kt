package com.dicoding.harvestscan.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.harvestscan.data.local.room.HarvestScanDao
import com.dicoding.harvestscan.data.local.room.Plant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ReminderViewModel(private val plantRepository: HarvestScanDao) : ViewModel() {

    val allPlants: Flow<List<Plant>> = plantRepository.allPlants

    fun insertPlant(plant: Plant) = viewModelScope.launch {
        plantRepository.insertPlant(plant)
    }
}
