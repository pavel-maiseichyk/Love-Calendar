package com.paulmais.lovecalendar.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MeetingEntity(
    @PrimaryKey(autoGenerate = false)
    val isoString: String
)
