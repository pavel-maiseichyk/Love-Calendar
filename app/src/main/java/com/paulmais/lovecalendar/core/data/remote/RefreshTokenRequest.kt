package com.paulmais.lovecalendar.core.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenRequest(
    val refreshToken: String
)