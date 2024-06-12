package com.dicoding.harvestscan.data.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plants")
data class Plant(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val type: String,
    val botanicalName: String,
    val imageUri: String
)
