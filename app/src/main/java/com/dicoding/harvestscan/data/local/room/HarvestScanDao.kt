package com.dicoding.harvestscan.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface HarvestScanDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlant(plant: Plant)

    @Delete
    suspend fun deletePlant(plant: Plant)

    @Query("SELECT * FROM plants")
    fun getAllPlants(): Flow<List<Plant>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReminder(reminder: Reminder)

    @Query("SELECT * FROM reminders")
    fun getAllReminders(): LiveData<List<Reminder>>

    @Query("SELECT * FROM plants WHERE id = :plantId")
    fun getPlantById(plantId: Int): LiveData<Plant>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(scanHistory: ScanHistory)
    @Query("SELECT * FROM history ORDER BY id DESC")
    fun getAllHistory(): LiveData<List<ScanHistory>>

    @Delete
    suspend fun deleteHistory(scanHistory: ScanHistory)
    @Query("DELETE FROM history WHERE id = :historyId")
    suspend fun deleteHistoryById(historyId: Int)
}
