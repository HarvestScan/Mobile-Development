package com.dicoding.harvestscan.data.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface HarvestScanDao {
    // Operasi untuk Plant
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlant(plant: Plant)

    @Delete
    suspend fun deletePlant(plant: Plant)

    @Query("SELECT * FROM plants")
    fun getAllPlants(): Flow<List<Plant>> // Menggunakan Flow untuk pengambilan data secara asinkron

    // Operasi untuk Reminder
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReminder(reminder: Reminder)

    @Query("SELECT * FROM reminders")
    fun getAllReminders(): Flow<List<Reminder>> // Pastikan Reminder diimport dengan benar

    @Query("DELETE FROM reminders WHERE plantName = :plantName")
    suspend fun deleteRemindersByPlantName(plantName: String)

    @Transaction
    suspend fun deletePlantAndReminders(plant: Plant) {
        deleteRemindersByPlantName(plant.name)
        deletePlant(plant)
    }
}
