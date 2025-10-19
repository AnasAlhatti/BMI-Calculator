package com.Anas.bmicalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.Anas.bmicalculator.data.BMIHistoryDatabase
import com.Anas.bmicalculator.data.repository.BMIRepositoryImpl
import com.Anas.bmicalculator.domain.usecase.DeleteAllBMIRecordsUseCase
import com.Anas.bmicalculator.domain.usecase.DeleteBMIRecordUseCase
import com.Anas.bmicalculator.domain.usecase.GetBMIHistoryUseCase
import com.Anas.bmicalculator.domain.usecase.SaveBMIRecordUseCase
import com.Anas.bmicalculator.navigation.AppNavHost
import com.Anas.bmicalculator.ui.theme.BMICalculatorTheme
import com.Anas.bmicalculator.viewmodel.BMIHistoryViewModel
import com.Anas.bmicalculator.viewmodel.BMIHistoryViewModelFactory

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
