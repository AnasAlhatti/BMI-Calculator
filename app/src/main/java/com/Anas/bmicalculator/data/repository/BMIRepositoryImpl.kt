package com.Anas.bmicalculator.data.repository

import com.Anas.bmicalculator.data.BMIDao
import com.Anas.bmicalculator.domain.repository.BMIRepository
import com.Anas.bmicalculator.model.BMIRecord
import kotlinx.coroutines.flow.Flow

class BMIRepositoryImpl(private val dao: BMIDao) : BMIRepository {
    override suspend fun insertRecord(record: BMIRecord) = dao.insert(record)

    override suspend fun deleteRecord(record: BMIRecord) = dao.delete(record)

    override suspend fun deleteAllRecords() = dao.deleteAll()

    override fun getAllRecords(): Flow<List<BMIRecord>> = dao.getAllRecords()
}