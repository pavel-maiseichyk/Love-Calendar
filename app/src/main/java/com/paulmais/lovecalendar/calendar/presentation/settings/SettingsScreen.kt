package com.paulmais.lovecalendar.calendar.presentation.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.paulmais.lovecalendar.R
import com.paulmais.lovecalendar.calendar.presentation.settings.components.SettingsComponent
import com.paulmais.lovecalendar.calendar.presentation.settings.components.SettingsTopBar
import com.paulmais.lovecalendar.core.presentation.UiText
import com.paulmais.lovecalendar.core.presentation.component.AppButton
import com.paulmais.lovecalendar.core.presentation.ui.theme.LoveCalendarTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsScreenRoot(
    viewModel: SettingsViewModel = koinViewModel(),
    showSnackbar: (UiText) -> Unit,
    onBackClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        viewModel.uiChannel.collect { event ->
            when (event) {
                SettingsEvent.NavigateBack -> onBackClick()
                is SettingsEvent.ShowMessage -> showSnackbar(event.uiText)
                SettingsEvent.Logout -> {
                    showSnackbar(UiText.StringResource(R.string.success))
                    onLogoutClick()
                }
            }
        }
    }

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
            SettingsTopBar(
                text = stringResource(R.string.settings),
                leftButtonIcon = painterResource(R.drawable.arrow_back),
                onLeftButtonClick = { onAction(SettingsAction.BackClicked) }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(horizontal = 16.dp, vertical = 20.dp)
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            SettingsComponent(
                title = stringResource(R.string.special_date),
                isEditing = state.isEditingSpecialDate,
                onComponentClick = { onAction(SettingsAction.DateClicked) },
                onDoneClick = { onAction(SettingsAction.UpdateDateClicked) },
                onCancelClick = { onAction(SettingsAction.CancelUpdateDateClicked) },
                date = state.specialDateString,
                onDateChange = { onAction(SettingsAction.DateChanged(it)) }
            )
            Spacer(Modifier.height(20.dp))
            AppButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                onClick = { onAction(SettingsAction.LogoutClicked) },
                text = stringResource(R.string.log_out),
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