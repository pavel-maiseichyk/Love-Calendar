package com.paulmais.lovecalendar.calendar.domain.model

import kotlinx.datetime.LocalDate

data class User(
    val id: String,
    val email: String,
    val name: String,
    val specialDate: LocalDate?,
    val meetings: List<LocalDate>
)