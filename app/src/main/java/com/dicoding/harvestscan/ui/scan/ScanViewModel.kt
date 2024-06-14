package com.dicoding.harvestscan.ui.scan


import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.harvestscan.data.PlantRepository
import com.dicoding.harvestscan.data.local.room.PlantDatabase
import com.dicoding.harvestscan.data.local.room.ScanHistory
import kotlinx.coroutines.launch

class ScanViewModel(application: Application) : ViewModel() {
    private val historyDao = PlantDatabase.getDatabase(application).HarvestScanDao()

    // LiveData for scan history list
    private val _historyList = MutableLiveData<List<ScanHistory>>()
    val historyList: LiveData<List<ScanHistory>> = _historyList

    // LiveData for individual scan result
    private val _scanResult = MutableLiveData<ScanHistory?>()
    val scanResult: LiveData<ScanHistory?> = _scanResult

    init {
        // Observe the LiveData from the DAO and update the MutableLiveData
        historyDao.getAllHistory().observeForever {
            _historyList.postValue(it)
        }
    }

    // Add a new scan history entry
    fun addScanHistory(scanHistory: ScanHistory) {
        viewModelScope.launch {
            historyDao.insertHistory(scanHistory)
        }
    }

    // Load scan history by ID
//    fun loadScanHistoryById(scanId: Int) {
//        viewModelScope.launch {
//            val scanHistory = historyDao.getScanHistoryById(scanId).value
//            _scanResult.postValue(scanHistory)
//        }
//    }

//    private val repository: PlantRepository
//    val allHistory: LiveData<List<ScanHistory>>
//
//
//    init {
//        val harvestScanDao = PlantDatabase.getDatabase(application).HarvestScanDao()
//        repository = PlantRepository(harvestScanDao)
//        allHistory = repository.allHistory
//    }
//
//    fun insert(scanHistory: ScanHistory) = viewModelScope.launch {
//        repository.insertHistory(scanHistory)
//    }

}