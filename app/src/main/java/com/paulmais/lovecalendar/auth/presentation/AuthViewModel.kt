package com.paulmais.lovecalendar.auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paulmais.lovecalendar.auth.domain.AuthRepository
import com.paulmais.lovecalendar.core.domain.DataError
import com.paulmais.lovecalendar.core.domain.Result
import com.paulmais.lovecalendar.core.presentation.asUiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(AuthState())
    val state = _state.asStateFlow()

    private var _events = Channel<AuthEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: AuthAction) {
        when (action) {
            AuthAction.AuthTypeChanged -> {
                _state.update { it.copy(authType = AuthType.changeAuthType(state.value.authType)) }
            }

            is AuthAction.LoginChanged -> {
                _state.update { it.copy(login = action.login) }
            }

            is AuthAction.PasswordChanged -> {
                _state.update { it.copy(password = action.password) }
            }

            AuthAction.MainButtonClicked -> {
                viewModelScope.launch {
                    val result = when (state.value.authType) {
                        AuthType.Login -> authRepository.login(
                            email = state.value.login,
                            password = state.value.password
                        )

                        AuthType.Register -> authRepository.register(
                            email = state.value.login,
                            password = state.value.password
                        )
                    }

                    when (result) {
                        is Result.Error<DataError.Network> -> {
                            _events.send(AuthEvent.ShowError(result.error.asUiText()))
                        }

                        is Result.Success<Unit> -> {
                            _events.send(AuthEvent.CompleteSign)
                        }
                    }
                }
            }
        }
    }
}