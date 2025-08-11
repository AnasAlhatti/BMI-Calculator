package com.example.bmicalculator

import com.example.bmicalculator.model.BMIState
import com.example.bmicalculator.viewmodel.BMIViewModel
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class BMIViewModelTest {

    private lateinit var viewModel: BMIViewModel

    @Before
    fun setup() {
        viewModel = BMIViewModel()
    }

    @Test
    fun calculateBMI_metric_validInput_returnsCorrectCategory() {
        viewModel.onWeightChange("70")
        viewModel.onHeightChange("170")

        viewModel.calculateBMI()

        val state = viewModel.bmiState.value
        assertTrue(state is BMIState.Success)
        val success = state as BMIState.Success
        assertEquals("Normal", success.category)
        assertEquals(24.22, success.bmi, 0.1) // delta = 0.1 tolerance
    }

    @Test
    fun calculateBMI_imperial_validInput_returnsCorrectCategory() {
        viewModel.onUnitToggle()
        viewModel.onWeightChange("154")  // ~70 kg
        viewModel.onHeightChange("67")   // ~170 cm

        viewModel.calculateBMI()

        val state = viewModel.bmiState.value
        assertTrue(state is BMIState.Success)
        val success = state as BMIState.Success
        assertEquals("Normal", success.category)
        assertEquals(24.1, success.bmi, 0.2)
    }

    @Test
    fun calculateBMI_invalidInput_setsErrorState() {
        viewModel.onWeightChange("abc")
        viewModel.onHeightChange("xyz")

        viewModel.calculateBMI()

        val state = viewModel.bmiState.value
        assertTrue(state is BMIState.Error)
        val error = state as BMIState.Error
        assertEquals("Please enter valid numbers", error.message)
    }

    @Test
    fun toggleUnit_switchesBetweenMetricAndImperial() {
        assertEquals(BMIViewModel.UnitType.METRIC, viewModel.unitType.value)

        viewModel.onUnitToggle()
        assertEquals(BMIViewModel.UnitType.IMPERIAL, viewModel.unitType.value)

        viewModel.onUnitToggle()
        assertEquals(BMIViewModel.UnitType.METRIC, viewModel.unitType.value)
    }

    @Test
    fun inputChange_setsIdleState() {
        viewModel.onWeightChange("70")
        viewModel.onHeightChange("170")
        viewModel.calculateBMI()

        assertTrue(viewModel.bmiState.value is BMIState.Success)

        // Now change height, state should reset
        viewModel.onHeightChange("180")
        assertEquals(BMIState.Idle, viewModel.bmiState.value)
    }

}