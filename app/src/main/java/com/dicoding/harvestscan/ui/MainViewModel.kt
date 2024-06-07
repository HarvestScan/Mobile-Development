package com.dicoding.harvestscan.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    private val _navigateToScan = MutableLiveData<Boolean>()
    val navigateToScan: LiveData<Boolean> get() = _navigateToScan

    private val _navigateToMyPlant = MutableLiveData<Boolean>()
    val navigateToMyPlant: LiveData<Boolean> get() = _navigateToMyPlant

    fun onScanButtonClicked() {
        _navigateToScan.value = true
    }

    fun onNavigatedToScan() {
        _navigateToScan.value = false
    }
    fun onMyPlantButtonClicked() {
        _navigateToMyPlant.value = true
    }

    fun onNavigatedToMyPlant() {
        _navigateToMyPlant.value = false
    }
}