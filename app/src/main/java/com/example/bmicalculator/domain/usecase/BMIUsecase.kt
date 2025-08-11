package com.example.bmicalculator.domain.usecase

import com.example.bmicalculator.domain.repository.BMIRepository
import com.example.bmicalculator.model.BMIRecord
import kotlinx.coroutines.flow.Flow

class SaveBMIRecordUseCase(private val repository: BMIRepository) {
    suspend operator fun invoke(record: BMIRecord) = repository.insertRecord(record)
}

class DeleteBMIRecordUseCase(private val repository: BMIRepository) {
    suspend operator fun invoke(record: BMIRecord) = repository.deleteRecord(record)
}

class DeleteAllBMIRecordsUseCase(private val repository: BMIRepository) {
    suspend operator fun invoke() = repository.deleteAllRecords()
}

class GetBMIHistoryUseCase(private val repository: BMIRepository) {
    operator fun invoke(): Flow<List<BMIRecord>> = repository.getAllRecords()
}
