package com.example.bmicalculator.domain.repository

import com.example.bmicalculator.model.BMIRecord
import kotlinx.coroutines.flow.Flow

interface BMIRepository {
    suspend fun insertRecord(record: BMIRecord)
    suspend fun deleteRecord(record: BMIRecord)
    suspend fun deleteAllRecords()
    fun getAllRecords(): Flow<List<BMIRecord>>
}
