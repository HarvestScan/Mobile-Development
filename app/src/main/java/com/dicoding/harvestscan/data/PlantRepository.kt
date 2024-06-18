package com.dicoding.harvestscan.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.dicoding.harvestscan.data.local.room.HarvestScanDao
import com.dicoding.harvestscan.data.local.room.Plant
import com.dicoding.harvestscan.data.local.room.Reminder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PlantRepository(private val harvestScanDao: HarvestScanDao) {

    fun getAllPlants(): LiveData<List<Plant>> {
        return harvestScanDao.getAllPlants().asLiveData(Dispatchers.IO)
    }

    fun getPlantById(plantId: Int): LiveData<Plant> {
        return harvestScanDao.getPlantById(plantId)
    }

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

    fun getAllReminders(): LiveData<List<Reminder>> {
        return harvestScanDao.getAllReminders()
    }

    suspend fun deleteReminderById(reminderId: Int) {
        withContext(Dispatchers.IO) {
            harvestScanDao.deleteReminderById(reminderId)
        }
    }

}
