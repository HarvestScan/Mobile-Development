package com.dicoding.harvestscan.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.harvestscan.data.local.room.Reminder
import com.dicoding.harvestscan.data.local.room.ReminderDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReminderViewModel @Inject constructor(
    private val reminderDao: ReminderDao
) : ViewModel() {
    fun insertReminder(reminder: Reminder) {
        viewModelScope.launch {
            reminderDao.insert(reminder)
        }
    }
}
