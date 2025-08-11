package com.example.bmicalculator.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bmicalculator.domain.usecase.*
import com.example.bmicalculator.model.BMIRecord
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class BMIHistoryViewModel(
    getHistoryUseCase: GetBMIHistoryUseCase,
    private val saveUseCase: SaveBMIRecordUseCase,
    private val deleteUseCase: DeleteBMIRecordUseCase,
    private val clearUseCase: DeleteAllBMIRecordsUseCase
) : ViewModel() {

    val history: StateFlow<List<BMIRecord>> = getHistoryUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun save(record: BMIRecord) {
        viewModelScope.launch {
            saveUseCase(record)
        }
    }

    fun delete(record: BMIRecord) {
        viewModelScope.launch {
            deleteUseCase(record)
        }
    }

    fun clearAll() {
        viewModelScope.launch {
            clearUseCase()
        }
    }
}
