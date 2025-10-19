package com.Anas.bmicalculator.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bmi_history")
data class BMIRecord(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val bmi: Double,
    val category: String,
    val weight: Double,
    val height: Double,
    val unit: String,
    val timestamp: Long = System.currentTimeMillis()
)
