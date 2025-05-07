package com.paulmais.lovecalendar.auth.presentation

sealed interface AuthAction {
    data class LoginChanged(val login: String) : AuthAction
    data class PasswordChanged(val password: String) : AuthAction
    data object MainButtonClicked : AuthAction
    data object AuthTypeChanged : AuthAction
}