package com.paulmais.lovecalendar.calendar.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class SyncInfo(
    val nameSync: SyncStatus,
    val emailSync: SyncStatus,
    val anniversarySync: SyncStatus
)

val pendingSyncInfo = SyncInfo(SyncStatus.Pending, SyncStatus.Pending, SyncStatus.Pending)
val syncedSyncInfo = SyncInfo(SyncStatus.Synced, SyncStatus.Synced, SyncStatus.Synced)

fun SyncInfo.isPending(): Boolean {
    return nameSync == SyncStatus.Pending ||
            emailSync == SyncStatus.Pending ||
            anniversarySync == SyncStatus.Pending
}