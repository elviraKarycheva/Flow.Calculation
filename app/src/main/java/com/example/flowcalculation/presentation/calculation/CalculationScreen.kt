package com.example.flowcalculation.presentation.calculation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.flowcalculation.R

@Composable
fun CalculationScreen(
    viewModel: CalculationViewModel = hiltViewModel(),
) {
    val state = viewModel.viewState.collectAsStateWithLifecycle()
    CalculationContent(
        state = state.value,
        onDigitValueChanged = { viewModel.onTextChanged(it) },
        onButtonClick = { viewModel.onStartButtonClicked() },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculationContent(
    state: CalculationUiState,
    onDigitValueChanged: (String) -> Unit,
    onButtonClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(all = 24.dp),
    ) {
        TextField(
            value = state.numberString,
            label = { Text(text = stringResource(id = R.string.input_field_hint)) },
            onValueChange = onDigitValueChanged,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(
            modifier = Modifier.height(16.dp),
        )
        Button(
            onClick = onButtonClick,
            content = { Text(text = stringResource(id = R.string.calculate)) },
            modifier = Modifier.fillMaxWidth(),
        )
        Text(
            text = state.calculationResult,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Preview(device = Devices.PIXEL_4)
@Composable
fun SquareComposablePreview() {
    CalculationContent(
        state = CalculationUiState(currentNumber = 7, currentSum = 0, calculationResult = "1 3 6 10 15 21 28"),
        onDigitValueChanged = {},
        onButtonClick = {},
    )
}