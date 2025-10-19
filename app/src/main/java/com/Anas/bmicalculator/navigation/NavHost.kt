package com.Anas.bmicalculator.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.Anas.bmicalculator.ui.BMICalculatorScreen
import com.Anas.bmicalculator.ui.screens.BMIHistoryScreen
import com.Anas.bmicalculator.viewmodel.BMIHistoryViewModel

@Composable
fun AppNavHost(
    navController: NavHostController,
    historyViewModel: BMIHistoryViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Calculator.route,
        modifier = modifier
    ) {
        composable(Screen.Calculator.route) {
            BMICalculatorScreen(
                onHistoryClick = { navController.navigate(Screen.History.route) },
                onSaveResult = { historyViewModel.save(it) }
            )
        }
        composable(Screen.History.route) {
            BMIHistoryScreen(
                viewModel = historyViewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
