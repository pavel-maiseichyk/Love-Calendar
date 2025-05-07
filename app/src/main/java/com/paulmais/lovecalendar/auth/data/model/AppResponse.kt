package com.paulmais.lovecalendar.auth.data.model

import com.paulmais.lovecalendar.calendar.data.remote.UserDTO
import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val errorCode: String,
    val message: String
)

@Serializable
data class UserResponse(
    val user: UserDTO
)