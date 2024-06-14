package com.dicoding.harvestscan.data.local.room

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation

// Plant entity
@Entity(tableName = "plants")
data class Plant(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val type: String,
    val botanicalName: String,
    val imageUri: String
)

// Reminder entity with foreign key reference to Plant
@Entity(
    tableName = "reminders",
    foreignKeys = [ForeignKey(
        entity = Plant::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("plantId"),
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("plantId")]
)
data class Reminder(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val plantId: Int,
    val reminderTime: String,
    val daysOfWeek: String,
    val notes: String
)

// Data class for the relationship between Plant and Reminder
data class PlantWithReminders(
    @Embedded val plant: Plant,
    @Relation(
        parentColumn = "id",
        entityColumn = "plantId"
    )
    val reminders: List<Reminder>
)

@Entity(tableName = "history")
data class ScanHistory(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val label: String,
    val confidenceScore: Float,
    val description: String,
    val tips: String,
    val imageUri: String
)