package com.paulmais.lovecalendar.auth.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.paulmais.lovecalendar.R
import com.paulmais.lovecalendar.auth.presentation.components.AuthBottomSection
import com.paulmais.lovecalendar.auth.presentation.components.AuthMiddleSection
import com.paulmais.lovecalendar.auth.presentation.components.WelcomeText
import com.paulmais.lovecalendar.core.presentation.ObserveAsEvents
import com.paulmais.lovecalendar.core.presentation.UiText
import com.paulmais.lovecalendar.core.presentation.ui.theme.LoveCalendarTheme
import com.paulmais.lovecalendar.core.presentation.ui.theme.red_container
import org.koin.androidx.compose.koinViewModel

@Composable
fun AuthScreenRoot(
    viewModel: AuthViewModel = koinViewModel<AuthViewModel>(),
    showSnackbar: (UiText) -> Unit,
    onLoginSuccess: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is AuthEvent.ShowError -> {
                keyboardController?.hide()
                showSnackbar(event.error)
            }

            AuthEvent.CompleteSign -> {
                keyboardController?.hide()
                showSnackbar(UiText.StringResource(R.string.success))
                onLoginSuccess()
            }
        }
    }

    AuthScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun AuthScreen(
    state: AuthState,
    onAction: (AuthAction) -> Unit,
) {
    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(red_container)
                .padding(
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    top = innerPadding.calculateTopPadding(),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                    bottom = 0.dp
                ),
            contentAlignment = Alignment.BottomCenter
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 36.dp, topEnd = 36.dp))
                    .background(Color.White)
            ) {
                Column(
                    modifier = Modifier.padding(all = 32.dp).padding(bottom = innerPadding.calculateBottomPadding()),
                    verticalArrangement = Arrangement.spacedBy(48.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    WelcomeText()
                    AuthMiddleSection(
                        login = state.login,
                        onLoginChange = { onAction(AuthAction.LoginChanged(it)) },
                        password = state.password,
                        onPasswordChange = { onAction(AuthAction.PasswordChanged(it)) },
                        authType = state.authType,
                        onMainButtonClick = { onAction(AuthAction.MainButtonClicked) }
                    )
                    AuthBottomSection(
                        authType = state.authType,
                        onChangeAuthTypeClick = { onAction(AuthAction.AuthTypeChanged) }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    LoveCalendarTheme {
        AuthScreen(
            state = AuthState(),
            onAction = {}
        )
    }
}