package com.example.bmicalculator.viewmodel

import androidx.lifecycle.ViewModel
import com.example.bmicalculator.model.BMIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class BMIViewModel : ViewModel() {

    private val _weightInput = MutableStateFlow("")
    val weightInput: StateFlow<String> = _weightInput

    private val _heightInput = MutableStateFlow("")
    val heightInput: StateFlow<String> = _heightInput

    private val _bmiState = MutableStateFlow<BMIState>(BMIState.Idle)
    val bmiState: StateFlow<BMIState> = _bmiState

    fun onWeightChange(value: String) {
        _weightInput.value = value
        _bmiState.value = BMIState.Idle
    }

    fun onHeightChange(value: String) {
        _heightInput.value = value
        _bmiState.value = BMIState.Idle
    }
    fun calculateBMI() {
        val weight = _weightInput.value.toDoubleOrNull()
        val height = _heightInput.value.toDoubleOrNull()

        if (weight != null && height != null && height > 0) {
            val bmi = when (_unitType.value) {
                UnitType.METRIC -> {
                    val heightM = height / 100
                    weight / (heightM * heightM)
                }
                UnitType.IMPERIAL -> {
                    // height in inches, weight in pounds
                    703.0 * weight / (height * height)
                }
            }

            val category = when {
                bmi < 18.5 -> "Underweight"
                bmi <= 24.9 -> "Normal"
                bmi <= 29.9 -> "Overweight"
                else -> "Obese"
            }

            _bmiState.value = BMIState.Success(bmi, category)
        } else {
            _bmiState.value = BMIState.Error("Please enter valid numbers")
        }
    }

    enum class UnitType { METRIC, IMPERIAL }

    private val _unitType = MutableStateFlow(UnitType.METRIC)
    val unitType: StateFlow<UnitType> = _unitType

    fun onUnitToggle() {
        _unitType.value = if (_unitType.value == UnitType.METRIC) UnitType.IMPERIAL else UnitType.METRIC
    }

}
