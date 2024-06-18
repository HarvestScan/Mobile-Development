package com.dicoding.harvestscan.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    private val _navigateToHome = MutableLiveData<Boolean>()
    val navigateToHome: LiveData<Boolean> get() = _navigateToHome

    private val _navigateToScan = MutableLiveData<Boolean>()
    val navigateToScan: LiveData<Boolean> get() = _navigateToScan

    private val _navigateToHistory = MutableLiveData<Boolean>()
    val navigateToHistory: LiveData<Boolean> get() = _navigateToHistory

    private val _navigateToPlantsMenu = MutableLiveData<Int>()
    val navigateToPlantsMenu: LiveData<Int> get() = _navigateToPlantsMenu

    private val _navigateToAddReminder = MutableLiveData<Boolean>()
    val navigateToAddReminder: LiveData<Boolean> get() = _navigateToAddReminder

    fun onHomeButtonClicked() {
        _navigateToHome.value = true
    }

    fun onNavigatedToHome() {
        _navigateToHome.value = false
    }

    fun onScanButtonClicked() {
        _navigateToScan.value = true
    }

    fun onNavigatedToScan() {
        _navigateToScan.value = false
    }

    fun onHistoryButtonClicked() {
        _navigateToHistory.value = true
    }

    fun onNavigatedToHistory() {
        _navigateToHistory.value = false
    }

    fun onMyPlantButtonClicked() {
        _navigateToPlantsMenu.value = 0
    }

    fun onNavigatedToPlantsMenu() {
        _navigateToPlantsMenu.value = -1
    }

    fun onReminderButtonClicked() {
        _navigateToAddReminder.value = true
    }
    fun onNavigatedToAddReminder() {
        _navigateToAddReminder.value = false
    }
}
