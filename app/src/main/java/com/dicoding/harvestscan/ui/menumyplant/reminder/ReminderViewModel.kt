package com.dicoding.harvestscan.ui.menumyplant.reminder

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.harvestscan.data.PlantRepository
import com.dicoding.harvestscan.data.local.room.Plant
import com.dicoding.harvestscan.data.local.room.PlantDatabase
import com.dicoding.harvestscan.data.local.room.Reminder
import kotlinx.coroutines.launch


class ReminderViewModel (application: Application) : ViewModel() {
    private val repository: PlantRepository
    init {
        val dao = PlantDatabase.getDatabase(application).HarvestScanDao()
        repository = PlantRepository(dao)
    }
    fun insertReminder(reminder: Reminder) {
        viewModelScope.launch {
            repository.insertReminder(reminder)
        }
    }
    fun getAllReminders(): LiveData<List<Reminder>> {
        return repository.getAllReminders()
    }

    fun deleteReminderById(reminderId: Int) {
        viewModelScope.launch {
            repository.deleteReminderById(reminderId)
        }
    }

    fun getAllPlants(): LiveData<List<Plant>> {
        return repository.getAllPlants()
    }
}
