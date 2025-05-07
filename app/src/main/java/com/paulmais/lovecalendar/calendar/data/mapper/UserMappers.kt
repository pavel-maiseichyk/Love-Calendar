package com.paulmais.lovecalendar.calendar.data.mapper

import com.paulmais.lovecalendar.calendar.data.remote.UserDTO
import com.paulmais.lovecalendar.calendar.domain.model.User
import kotlinx.datetime.LocalDate

fun User.toDTO(): UserDTO {
    return UserDTO(
        id = id,
        email = email,
        name = name,
        specialDate = specialDate.toString(),
        meetings = meetings.map { it.toString() }
    )
}

fun UserDTO.toDomainModel(): User {
    return User(
        id = id,
        email = email,
        name = name,
        specialDate = LocalDate.parse(specialDate),
        meetings = meetings.map { LocalDate.parse(it) }
    )
}