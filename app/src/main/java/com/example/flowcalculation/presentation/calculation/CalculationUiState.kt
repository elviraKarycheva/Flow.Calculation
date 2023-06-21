package com.example.flowcalculation.presentation.calculation

data class CalculationUiState(
    val currentNumber: Int?,
    val currentSum: Int,
    val calculationResult: String,
) {
    val numberString = currentNumber?.toString() ?: ""
}