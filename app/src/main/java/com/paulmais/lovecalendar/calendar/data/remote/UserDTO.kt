package com.paulmais.lovecalendar.calendar.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class UserDTO(
    val id: String,
    val email: String,
    val name: String,
    val specialDate: String,
    val meetings: List<String>
)