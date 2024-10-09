package com.paulmais.lovecalendar.presentation.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.paulmais.lovecalendar.presentation.settings.components.SpecialDateTextField
import com.paulmais.lovecalendar.presentation.ui.theme.LoveCalendarTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsScreenRoot(
    viewModel: SettingsViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    SettingsScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
private fun SettingsScreen(
    state: SettingsState,
    onAction: (SettingsAction) -> Unit
) {
    SpecialDateTextField(
        textFieldState = state.specialDate
    )
}

@Preview
@Composable
private fun SettingsScreenPreview() {
    LoveCalendarTheme {
        SettingsScreen(
            state = SettingsState(),
            onAction = {}
        )
    }
}