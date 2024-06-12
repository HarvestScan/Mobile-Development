package com.dicoding.harvestscan.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PlantDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(plant: Plant) // Hapus suspend

    @Query("SELECT * FROM plants")
    fun getAllPlants(): Flow<List<Plant>>
}

