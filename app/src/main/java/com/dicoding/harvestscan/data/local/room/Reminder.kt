package com.dicoding.harvestscan.data.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reminders")
data class Reminder(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val plantName: String,
    val reminderTime: String,
    val daysOfWeek: String,
    val notes: String
)
