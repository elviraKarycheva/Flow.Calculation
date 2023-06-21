package com.example.flowcalculation.presentation.calculation

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
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.flowcalculation.R


@Composable
fun SumScreen(
    viewModel: CalculationViewModel = hiltViewModel(),
) {
    val state = viewModel.viewState.collectAsState()
    State(state, { viewModel.onTextChanged(it) }, { viewModel.onStartButtonClicked() })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun State(
    state: State<CalculationUiState>,
    onDigitValueChanged: (String) -> Unit,
    onButtonClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 24.dp),
    ) {
        TextField(
            value = state.value.digitString,
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
            text = state.value.calculationResult,
        )
    }
}