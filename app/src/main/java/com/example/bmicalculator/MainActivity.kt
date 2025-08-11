package com.example.bmicalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.bmicalculator.data.BMIHistoryDatabase
import com.example.bmicalculator.data.repository.BMIRepositoryImpl
import com.example.bmicalculator.domain.usecase.DeleteAllBMIRecordsUseCase
import com.example.bmicalculator.domain.usecase.DeleteBMIRecordUseCase
import com.example.bmicalculator.domain.usecase.GetBMIHistoryUseCase
import com.example.bmicalculator.domain.usecase.SaveBMIRecordUseCase
import com.example.bmicalculator.navigation.AppNavHost
import com.example.bmicalculator.ui.theme.BMICalculatorTheme
import com.example.bmicalculator.viewmodel.BMIHistoryViewModel
import com.example.bmicalculator.viewmodel.BMIHistoryViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Build dependencies for BMIHistoryViewModel
        val dao = BMIHistoryDatabase.getInstance(applicationContext).bmiDao()
        val repository = BMIRepositoryImpl(dao)
        val factory = BMIHistoryViewModelFactory(
            getHistory = GetBMIHistoryUseCase(repository),
            save = SaveBMIRecordUseCase(repository),
            delete = DeleteBMIRecordUseCase(repository),
            clear = DeleteAllBMIRecordsUseCase(repository)
        )
        val historyViewModel = ViewModelProvider(this, factory)[BMIHistoryViewModel::class.java]

        setContent {
            BMICalculatorTheme {
                val navController = rememberNavController()
                Scaffold { innerPadding ->
                    AppNavHost(
                        navController = navController,
                        historyViewModel = historyViewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
