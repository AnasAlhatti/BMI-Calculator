package com.Anas.bmicalculator

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BMICalculatorScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun checkTitleDisplayed() {
        composeTestRule
            .onNodeWithText("🧮 BMI Calculator")
            .assertIsDisplayed()
    }

    @Test
    fun initialIdleMessageIsDisplayed() {
        composeTestRule
            .onNodeWithText("Enter your weight and height to calculate BMI")
            .assertIsDisplayed()
    }

    @Test
    fun validMetricInputDisplaysBMIResult() {
        // Input weight
        composeTestRule
            .onNodeWithText("Weight (kg)")
            .performTextInput("70")

        // Input height
        composeTestRule
            .onNodeWithText("Height (cm)")
            .performTextInput("170")

        // Tap Calculate
        composeTestRule
            .onNodeWithText("Calculate")
            .performClick()

        // Check result appears
        composeTestRule
            .onNodeWithText("Your BMI")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Category: Normal")
            .assertIsDisplayed()
    }

    @Test
    fun switchingUnitsUpdatesInputLabels() {
        // Tap switch units
        composeTestRule
            .onNodeWithText("Switch Units")
            .performClick()

        // Check if unit label has changed to imperial
        composeTestRule
            .onNodeWithText("Weight (lb)")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Height (in)")
            .assertIsDisplayed()
    }

    @Test
    fun invalidInputShowsErrorMessage() {
        // Input invalid weight and height
        composeTestRule
            .onNodeWithText("Weight (kg)")
            .performTextInput("abc")

        composeTestRule
            .onNodeWithText("Height (cm)")
            .performTextInput("xyz")

        composeTestRule
            .onNodeWithText("Calculate")
            .performClick()

        // Check error appears
        composeTestRule
            .onNodeWithText("Please enter valid numbers")
            .assertIsDisplayed()
    }

}