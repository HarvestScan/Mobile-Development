package com.dicoding.harvestscan.data

import com.dicoding.harvestscan.data.local.room.HarvestScanDao
import com.dicoding.harvestscan.data.local.room.Plant

class PlantRepository(private val dao: HarvestScanDao) {

    val allPlants = dao.getAllPlants()

    suspend fun insertPlant(plant: Plant) {
        dao.insertPlant(plant)
    }

    suspend fun deletePlant(plant: Plant) {
        dao.deleteRemindersByPlantName(plant.name)
        dao.deletePlant(plant)
    }
}
