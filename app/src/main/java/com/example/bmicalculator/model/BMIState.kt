package com.example.bmicalculator.model

sealed class BMIState {
    object Idle : BMIState()
    object Loading : BMIState()
    data class Success(val bmi: Double, val category: String) : BMIState()
    data class Error(val message: String) : BMIState()
}
