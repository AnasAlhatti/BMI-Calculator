package com.example.bmicalculator.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.bmicalculator.viewmodel.BMIHistoryViewModel

@Composable
fun BMIHistoryScreen(
    viewModel: BMIHistoryViewModel,
    onBack: () -> Unit
) {
    val history = viewModel.history.collectAsState().value

    BackHandler(onBack = onBack)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = onBack) {
                Text("Back")
            }
            Text("BMI History", style = MaterialTheme.typography.headlineSmall)
            Button(onClick = { viewModel.clearAll() }) {
                Text("Clear All")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(history) { record ->
                val resultColor = when (record.category) {
                    "Obese" -> Color(0xFFD32F2F) // Red
                    "Overweight" -> Color(0xFFFFA000) // Amber
                    else -> Color(0xFF388E3C) // Green
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            val weightUnit = if (record.unit == "kg/cm") "kg" else "lb"
                            val heightUnit = if (record.unit == "kg/cm") "cm" else "in"
                            Text("BMI: %.2f".format(record.bmi), color = resultColor)
                            Text("Category: ${record.category}", color = resultColor)
                            Text("Weight: ${record.weight} $weightUnit")
                            Text("Height: ${record.height} $heightUnit")
                        }
                        Button(onClick = { viewModel.delete(record) }) {
                            Text("Delete")
                        }
                    }
                }
            }
        }
    }
}
