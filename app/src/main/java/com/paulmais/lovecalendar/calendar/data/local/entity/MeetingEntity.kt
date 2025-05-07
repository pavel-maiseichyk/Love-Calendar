package com.paulmais.lovecalendar.calendar.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.paulmais.lovecalendar.calendar.domain.model.SyncStatus

@Entity
data class MeetingEntity(
    @PrimaryKey(autoGenerate = false)
    val isoString: String,
    val syncStatus: SyncStatus = SyncStatus.Pending
)