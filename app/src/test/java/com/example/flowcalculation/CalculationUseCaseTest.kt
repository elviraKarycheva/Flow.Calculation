package com.example.flowcalculation

import app.cash.turbine.test
import com.example.flowcalculation.domain.logic.CalculationUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class CalculationUseCaseTest {

    private val useCase = CalculationUseCase()

    @Test
    fun `zero number provide empty flow`() = runTest {
        val outputFlow = useCase.getCalculationFlow(0)
        val result = outputFlow.toList()

        assertEquals(emptyList<Int>(), result)
    }

    @Test
    fun `7 number provide flow`() = runTest {
        val outputFlow = useCase.getCalculationFlow(7)
        val result = outputFlow.toList()

        assertEquals(expectedList, result)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `flow provide number each 100ms`() = runTest {
        useCase.getCalculationFlow(3).test {
            advanceTimeBy(100L)
            assertEquals(firstItem, awaitItem())

            advanceTimeBy(100L)
            assertEquals(secondItem, awaitItem())

            advanceTimeBy(100L)
            assertEquals(thirdItem, awaitItem())

            awaitComplete()
        }
    }
}