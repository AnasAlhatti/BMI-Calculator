package com.example.bmicalculator.data.repository

import com.example.bmicalculator.data.BMIDao
import com.example.bmicalculator.domain.repository.BMIRepository
import com.example.bmicalculator.model.BMIRecord
import kotlinx.coroutines.flow.Flow

class BMIRepositoryImpl(private val dao: BMIDao) : BMIRepository {
    override suspend fun insertRecord(record: BMIRecord) = dao.insert(record)

    override suspend fun deleteRecord(record: BMIRecord) = dao.delete(record)

    override suspend fun deleteAllRecords() = dao.deleteAll()

    override fun getAllRecords(): Flow<List<BMIRecord>> = dao.getAllRecords()
}