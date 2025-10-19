package com.Anas.bmicalculator.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BMIResultCard(bmi: Double, category: String, modifier: Modifier = Modifier) {
    val resultColor = when (category) {
        "Obese" -> Color(0xFFD32F2F) // Deep red
        "Overweight" -> Color(0xFFFFA000) // Amber
        else -> Color(0xFF388E3C) // Green
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Your BMI", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(
                text = String.format("%.2f", bmi),
                fontSize = 32.sp,
                color = resultColor,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Category: $category",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = resultColor
            )
        }
    }
}

