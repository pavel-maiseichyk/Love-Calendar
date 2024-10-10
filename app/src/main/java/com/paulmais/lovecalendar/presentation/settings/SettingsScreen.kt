package com.paulmais.lovecalendar.presentation.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.paulmais.lovecalendar.presentation.settings.components.SettingsComponent
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
    SettingsComponent(
        textFieldState = state.specialDateTextFieldState,
        title = "Special Date",
        isEditing = state.isEditingSpecialDate,
        onComponentClick = { onAction(SettingsAction.OnDateClick) },
        onDoneClick = { onAction(SettingsAction.OnUpdateDateClick) },
        onCancelClick = { onAction(SettingsAction.OnCancelUpdateDateClick) },
        date = state.specialDateString
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