package com.example.flowcalculation.presentation.calculation

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flowcalculation.domain.logic.CalculationUseCase
import com.example.flowcalculation.presentation.provideSavedState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

@HiltViewModel
class CalculationViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val calculationUseCase: CalculationUseCase,
) : ViewModel() {

    private val mutableState = MutableStateFlow(createInitialState())
    val viewState get() = mutableState.asStateFlow()

    private var calculationJob: Job? = null

    private fun createInitialState(): CalculationUiState {
        val savedState = savedStateHandle.provideSavedState {
            SavedState(
                number = viewState.value.currentNumber,
                calculationResult = viewState.value.calculationResult
            )
        }

        return CalculationUiState(
            currentNumber = savedState?.number,
            currentSum = 0,
            calculationResult = savedState?.calculationResult.orEmpty(),
        )
    }

    fun onTextChanged(text: String) {
        val newValue = text.trim().toIntOrNull()
        updateState { it.copy(currentNumber = newValue) }
    }

    fun onStartButtonClicked() {
        val currentDigit = viewState.value.currentNumber ?: return

        updateState { state ->
            state.copy(
                calculationResult = "${state.calculationResult}\n",
                currentSum = 0,
            )
        }

        calculationJob?.cancel()
        calculationJob = calculationUseCase.getCalculationFlow(currentDigit)
            .onEach { number ->
                updateState { state ->
                    val currentSum = state.currentSum + number
                    val newSumResult = "${state.calculationResult} $currentSum"
                    state.copy(
                        calculationResult = newSumResult,
                        currentSum = currentSum,
                    )
                }
            }.launchIn(viewModelScope)
    }

    private fun updateState(update: (CalculationUiState) -> CalculationUiState) {
        mutableState.update(update)
    }

    @Parcelize
    private class SavedState(
        val number: Int?,
        val calculationResult: String,
    ) : Parcelable
}