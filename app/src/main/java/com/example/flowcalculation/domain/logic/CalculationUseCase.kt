package com.example.flowcalculation.domain.logic

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.merge
import javax.inject.Inject

class CalculationUseCase @Inject constructor() {

    fun getCalculationFlow(digit: Int) = (0 until digit)
        .map { index ->
            flow {
                val value = index + 1
                delay(value * 100L)
                emit(value)
            }
        }
        .merge()
}