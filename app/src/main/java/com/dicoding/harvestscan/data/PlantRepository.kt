package com.dicoding.harvestscan.data

import PlantDao
import com.dicoding.harvestscan.data.local.room.Plant
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

    suspend fun delete(plant: Plant) {
        withContext(Dispatchers.IO) {
            plantDao.delete(plant)
        }
    }
}
