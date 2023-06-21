package com.example.flowcalculation.domain.logic

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.merge
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

class CalculationUseCase @Inject constructor() {
    fun getCalculationFlow(number: Int): Flow<Int> =
        (0 until number)
            .map { index ->
                flow {
                    val value = index + 1
                    delay((value * 100L).milliseconds)
                    emit(value)
                }
            }
            .merge()
}