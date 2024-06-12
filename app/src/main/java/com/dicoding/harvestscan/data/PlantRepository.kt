package com.dicoding.harvestscan.data

import com.dicoding.harvestscan.data.local.room.Plant
import com.dicoding.harvestscan.data.local.room.PlantDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class PlantRepository(private val plantDao: PlantDao) {

    val allPlants: Flow<List<Plant>> = plantDao.getAllPlants()

    suspend fun insert(plant: Plant) {
        withContext(Dispatchers.IO) {
            plantDao.insert(plant)
        }
    }
}
