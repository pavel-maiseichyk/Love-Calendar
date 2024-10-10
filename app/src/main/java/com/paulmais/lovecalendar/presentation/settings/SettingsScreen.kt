package com.paulmais.lovecalendar.presentation.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.paulmais.lovecalendar.presentation.components.MyTopBar
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
    Scaffold(
        topBar = {
            MyTopBar(text = "Settings")
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(20.dp))
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
    }
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