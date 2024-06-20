package com.dicoding.harvestscan.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Plant::class, Reminder::class, ScanHistory::class], version = 2, exportSchema = false)
abstract class PlantDatabase : RoomDatabase() {
    abstract fun HarvestScanDao(): HarvestScanDao

    companion object {
        @Volatile
        private var INSTANCE: PlantDatabase? = null

        fun getDatabase(context: Context): PlantDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PlantDatabase::class.java,
                    "plant_database"
                )
                    .addMigrations(MIGRATION_1_2)
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE plants RENAME TO plants_old")
                database.execSQL("""
                    CREATE TABLE plants (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        name TEXT NOT NULL,
                        type TEXT NOT NULL,
                        botanicalName TEXT,
                        imageUri TEXT NOT NULL
                    )
                """)
                database.execSQL("""
                    INSERT INTO plants (id, name, type, botanicalName, imageUri)
                    SELECT id, name, type, botanicalName, imageUri FROM plants_old
                """)
                database.execSQL("DROP TABLE plants_old")
            }
        }
    }
}
