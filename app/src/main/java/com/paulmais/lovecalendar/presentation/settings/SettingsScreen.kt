package com.paulmais.lovecalendar.presentation.settings

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.paulmais.lovecalendar.R
import com.paulmais.lovecalendar.presentation.settings.components.SettingsComponent
import com.paulmais.lovecalendar.presentation.settings.components.SettingsTopBar
import com.paulmais.lovecalendar.presentation.ui.theme.LoveCalendarTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsScreenRoot(
    viewModel: SettingsViewModel = koinViewModel(),
    onBackClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(true) {
        viewModel.events.collect { event ->
            when (event) {
                is SettingsEvent.ShowToast -> Toast.makeText(
                    context,
                    event.message,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    SettingsScreen(
        state = state,
        onAction = { action ->
            when (action) {
                SettingsAction.OnBackClick -> onBackClick()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
private fun SettingsScreen(
    state: SettingsState,
    onAction: (SettingsAction) -> Unit
) {
    Scaffold(
        topBar = {
            SettingsTopBar(
                text = stringResource(R.string.settings),
                leftButtonIcon = painterResource(R.drawable.arrow_back),
                onLeftButtonClick = { onAction(SettingsAction.OnBackClick) }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Color.White)
                .padding(horizontal = 16.dp, vertical = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            SettingsComponent(
                textFieldState = state.specialDateTextFieldState,
                title = stringResource(R.string.special_date),
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