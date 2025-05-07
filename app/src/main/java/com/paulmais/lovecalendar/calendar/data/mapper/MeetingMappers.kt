package com.paulmais.lovecalendar.calendar.data.mapper

import com.paulmais.lovecalendar.calendar.data.local.entity.MeetingEntity
import kotlinx.datetime.LocalDate

fun LocalDate.toMeetingEntity(): MeetingEntity {
    return MeetingEntity(
        isoString = this.toString()
    )
}

fun MeetingEntity.toLocalDate(): LocalDate {
    return LocalDate.parse(input = this.isoString)
}