package com.dicoding.harvestscan.ui.scan


import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.harvestscan.data.local.room.PlantDatabase
import com.dicoding.harvestscan.data.local.room.ScanHistory
import kotlinx.coroutines.launch

class ScanViewModel(application: Application) : ViewModel() {
    private val historyDao = PlantDatabase.getDatabase(application).HarvestScanDao()

    private val _historyList = MutableLiveData<List<ScanHistory>>()
    val historyList: LiveData<List<ScanHistory>> = _historyList

    init {
        historyDao.getAllHistory().observeForever {
            _historyList.postValue(it)
        }
    }

    fun addScanHistory(scanHistory: ScanHistory) {
        viewModelScope.launch {
            historyDao.insertHistory(scanHistory)
        }
    }

    fun deleteHistory(scanHistory: ScanHistory) {
        viewModelScope.launch {
            historyDao.deleteHistory(scanHistory)
        }
    }

}