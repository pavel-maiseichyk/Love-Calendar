package com.paulmais.lovecalendar.auth.domain.model

data class AuthInfo(
    val accessToken: String,
    val refreshToken: String
) {
    fun isEmpty(): Boolean {
        return accessToken.isBlank() || accessToken.isBlank()
    }
}