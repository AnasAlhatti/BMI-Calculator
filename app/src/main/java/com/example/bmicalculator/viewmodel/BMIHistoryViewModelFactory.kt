package com.example.bmicalculator.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bmicalculator.domain.usecase.DeleteAllBMIRecordsUseCase
import com.example.bmicalculator.domain.usecase.DeleteBMIRecordUseCase
import com.example.bmicalculator.domain.usecase.GetBMIHistoryUseCase
import com.example.bmicalculator.domain.usecase.SaveBMIRecordUseCase

class BMIHistoryViewModelFactory(
    private val getHistory: GetBMIHistoryUseCase,
    private val save: SaveBMIRecordUseCase,
    private val delete: DeleteBMIRecordUseCase,
    private val clear: DeleteAllBMIRecordsUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BMIHistoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BMIHistoryViewModel(getHistory, save, delete, clear) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
