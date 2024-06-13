package com.dicoding.harvestscan.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(reminder: Reminder)

    @Query("SELECT * FROM reminders")
    fun getAllReminders(): Flow<List<Reminder>>
}
