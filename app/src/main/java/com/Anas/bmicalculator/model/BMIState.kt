package com.Anas.bmicalculator.model

sealed class BMIState {
    object Idle : BMIState()
    data class Success(val bmi: Double, val category: String) : BMIState()
    data class Error(val message: String) : BMIState()
}
