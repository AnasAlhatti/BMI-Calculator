package com.example.bmicalculator.navigation

sealed class Screen(val route: String) {
    object Calculator : Screen("calculator")
    object History : Screen("history")
}
