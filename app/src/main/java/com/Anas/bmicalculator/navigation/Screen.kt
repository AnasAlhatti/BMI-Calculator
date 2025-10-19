package com.Anas.bmicalculator.navigation

sealed class Screen(val route: String) {
    object Calculator : Screen("calculator")
    object History : Screen("history")
}
