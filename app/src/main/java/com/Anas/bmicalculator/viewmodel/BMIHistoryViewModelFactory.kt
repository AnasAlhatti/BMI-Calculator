package com.Anas.bmicalculator.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.Anas.bmicalculator.domain.usecase.DeleteAllBMIRecordsUseCase
import com.Anas.bmicalculator.domain.usecase.DeleteBMIRecordUseCase
import com.Anas.bmicalculator.domain.usecase.GetBMIHistoryUseCase
import com.Anas.bmicalculator.domain.usecase.SaveBMIRecordUseCase

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
