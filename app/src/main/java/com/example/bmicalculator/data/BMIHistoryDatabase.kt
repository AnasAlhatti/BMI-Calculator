package com.example.bmicalculator.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.bmicalculator.model.BMIRecord

@Database(entities = [BMIRecord::class], version = 2, exportSchema = false)
abstract class BMIHistoryDatabase : RoomDatabase() {

    abstract fun bmiDao(): BMIDao

    companion object {
        @Volatile
        private var INSTANCE: BMIHistoryDatabase? = null

        // Migration: v1 -> v2 adds weight, height, unit columns with defaults
        val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE bmi_history ADD COLUMN weight REAL NOT NULL DEFAULT 0.0")
                db.execSQL("ALTER TABLE bmi_history ADD COLUMN height REAL NOT NULL DEFAULT 0.0")
                db.execSQL("ALTER TABLE bmi_history ADD COLUMN unit TEXT NOT NULL DEFAULT 'kg/cm'")
            }
        }

        fun getInstance(context: Context): BMIHistoryDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BMIHistoryDatabase::class.java,
                    "bmi_history_db"
                )
                    .addMigrations(MIGRATION_1_2)
                    // .fallbackToDestructiveMigration() // dev-only convenience
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
