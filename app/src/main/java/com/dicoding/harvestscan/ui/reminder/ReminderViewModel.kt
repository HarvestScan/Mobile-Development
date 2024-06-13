package com.dicoding.harvestscan.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.harvestscan.data.PlantRepository
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
}
