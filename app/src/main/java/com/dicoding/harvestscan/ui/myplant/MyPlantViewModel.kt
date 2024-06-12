package com.dicoding.harvestscan.ui.myplant

import android.app.Application
import androidx.lifecycle.*
import com.dicoding.harvestscan.data.PlantRepository
import com.dicoding.harvestscan.data.local.room.Plant
import com.dicoding.harvestscan.data.local.room.PlantDatabase
import kotlinx.coroutines.launch

class MyPlantViewModel(application: Application) : ViewModel() {

    private val repository: PlantRepository
    val allPlants: LiveData<List<Plant>>

    init {
        val plantDao = PlantDatabase.getDatabase(application).plantDao()
        repository = PlantRepository(plantDao)
        allPlants = repository.allPlants.asLiveData()
    }

    fun insert(plant: Plant) {
        viewModelScope.launch {
            repository.insert(plant)
        }
    }

    fun delete(plant: Plant) {
        viewModelScope.launch {
            repository.delete(plant)
        }
    }
}


