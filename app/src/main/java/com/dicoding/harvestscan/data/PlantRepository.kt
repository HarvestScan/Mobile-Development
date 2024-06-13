package com.dicoding.harvestscan.data

import com.dicoding.harvestscan.data.local.room.HarvestScanDao
import com.dicoding.harvestscan.data.local.room.Plant
import com.dicoding.harvestscan.data.local.room.Reminder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class PlantRepository(private val harvestScanDao: HarvestScanDao) {

    val allPlants: Flow<List<Plant>> = harvestScanDao.getAllPlants()

    suspend fun insertPlant(plant: Plant) {
        withContext(Dispatchers.IO) {
            harvestScanDao.insertPlant(plant)
        }
    }

    suspend fun deletePlant(plant: Plant) {
        withContext(Dispatchers.IO) {
            harvestScanDao.deletePlant(plant)
        }
    }

    suspend fun insertReminder(reminder: Reminder){
        withContext(Dispatchers.IO){
            harvestScanDao.insertReminder(reminder)
        }
    }
}
