package com.paulmais.lovecalendar.data.mapper

import com.paulmais.lovecalendar.data.MeetingEntity
import kotlinx.datetime.LocalDate

fun LocalDate.toMeetingEntity(): MeetingEntity {
    return MeetingEntity(
        isoString = this.toString()
    )
}

fun MeetingEntity.toLocalDate(): LocalDate {
    return LocalDate.parse(isoString = this.isoString)
}