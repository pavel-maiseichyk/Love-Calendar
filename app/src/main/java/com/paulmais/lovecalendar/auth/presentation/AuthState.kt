package com.paulmais.lovecalendar.auth.presentation

data class AuthState(
    val authType: AuthType = AuthType.Login,
    val login: String = "",
    val password: String = ""
)

enum class AuthType {
    Login, Register;

    companion object {
        fun changeAuthType(authType: AuthType): AuthType {
            return when (authType) {
                Login -> Register
                Register -> Login
            }
        }
    }
}