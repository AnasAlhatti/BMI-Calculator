package com.example.bmicalculator.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bmicalculator.model.BMIRecord
import com.example.bmicalculator.model.BMIState
import com.example.bmicalculator.ui.components.BMIResultCard
import com.example.bmicalculator.viewmodel.BMIViewModel

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun BMICalculatorScreen(
    modifier: Modifier = Modifier,
    viewModel: BMIViewModel = viewModel(),
    onHistoryClick: () -> Unit = {},
    onSaveResult: (BMIRecord) -> Unit = {}
) {
    val weight by viewModel.weightInput.collectAsState()
    val height by viewModel.heightInput.collectAsState()
    val bmiState by viewModel.bmiState.collectAsState()
    val unit by viewModel.unitType.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current

    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val isLandscape = maxWidth > maxHeight
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("🧮 BMI Calculator", fontSize = 28.sp, fontWeight = FontWeight.Bold)
                TextButton(onClick = onHistoryClick) { Text("History") }
            }

            UnitSwitchRow(unit = unit, onToggle = viewModel::onUnitToggle)

            val onCalculateAndMaybeSave: () -> Unit = {
                keyboardController?.hide()
                viewModel.calculateBMI()
                val state = viewModel.bmiState.value
                if (state is BMIState.Success) {
                    val w = weight.toDoubleOrNull()
                    val h = height.toDoubleOrNull()
                    if (w != null && h != null) {
                        val unitLabel = if (unit == BMIViewModel.UnitType.METRIC) "kg/cm" else "lb/in"
                        onSaveResult(
                            BMIRecord(
                                bmi = state.bmi,
                                category = state.category,
                                weight = w,
                                height = h,
                                unit = unitLabel
                            )
                        )
                    }
                }
            }

            if (isLandscape) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    InputForm(
                        weight = weight,
                        height = height,
                        unit = unit,
                        onWeightChange = viewModel::onWeightChange,
                        onHeightChange = viewModel::onHeightChange,
                        onCalculate = onCalculateAndMaybeSave,
                        modifier = Modifier.weight(1f)
                    )

                    ResultSection(
                        bmiState = bmiState,
                        modifier = Modifier.weight(1f)
                    )
                }
            } else {
                InputForm(
                    weight = weight,
                    height = height,
                    unit = unit,
                    onWeightChange = viewModel::onWeightChange,
                    onHeightChange = viewModel::onHeightChange,
                    onCalculate = onCalculateAndMaybeSave
                )

                ResultSection(
                    bmiState = bmiState,
                )
            }
        }
    }
}

@Composable
fun UnitSwitchRow(unit: BMIViewModel.UnitType, onToggle: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("Unit: ${if (unit == BMIViewModel.UnitType.METRIC) "Metric (kg/cm)" else "Imperial (lb/in)"}")
        Button(onClick = onToggle) { Text("Switch Units") }
    }
}

@Composable
fun InputForm(
    weight: String,
    height: String,
    unit: BMIViewModel.UnitType,
    onWeightChange: (String) -> Unit,
    onHeightChange: (String) -> Unit,
    onCalculate: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(4.dp)) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            TextField(
                value = weight,
                onValueChange = onWeightChange,
                label = { Text("Weight (${if (unit == BMIViewModel.UnitType.METRIC) "kg" else "lb"})") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = height,
                onValueChange = onHeightChange,
                label = { Text("Height (${if (unit == BMIViewModel.UnitType.METRIC) "cm" else "in"})") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            Button(onClick = onCalculate, modifier = Modifier.fillMaxWidth()) { Text("Calculate") }
        }
    }
}

@Composable
fun ResultSection(
    bmiState: BMIState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (bmiState) {
            is BMIState.Success -> {
                BMIResultCard(bmi = bmiState.bmi, category = bmiState.category)
            }
            is BMIState.Error -> {
                Text(bmiState.message, color = MaterialTheme.colorScheme.error)
            }
            BMIState.Idle -> {
                Text("Enter your weight and height to calculate BMI", fontSize = 16.sp)
            }
            else -> Unit
        }
    }
}
