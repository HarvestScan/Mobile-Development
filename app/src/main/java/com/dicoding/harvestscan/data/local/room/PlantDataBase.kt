package com.dicoding.harvestscan.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Plant::class, Reminder::class], version = 1, exportSchema = false)
abstract class PlantDatabase : RoomDatabase() {
    abstract fun plantDao(): PlantDao
    abstract fun reminderDao(): ReminderDao

    companion object {
        @Volatile
        private var INSTANCE: PlantDatabase? = null

        fun getDatabase(context: Context): PlantDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PlantDatabase::class.java,
                    "plant_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
