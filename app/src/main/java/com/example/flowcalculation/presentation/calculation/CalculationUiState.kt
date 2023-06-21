package com.example.flowcalculation.presentation.calculation

data class CalculationUiState(
    val currentNumber: Int? = null,
    val currentSum: Int = 0,
    val calculationResult: String = "",
) {
    val digitString = currentNumber?.toString() ?: ""
}