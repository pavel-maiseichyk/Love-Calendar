package com.paulmais.lovecalendar.calendar.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.paulmais.lovecalendar.calendar.data.local.entity.MeetingEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MeetingsDao {

    @Query("SELECT * FROM MeetingEntity")
    fun getMeetings(): Flow<List<MeetingEntity>>

    @Query("SELECT * FROM MeetingEntity WHERE syncStatus == 'Pending'")
    fun getPendingSyncMeetings(): Flow<List<MeetingEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeetings(meetings: List<MeetingEntity>)

    @Query("DELETE FROM MeetingEntity")
    suspend fun clearMeetings()

    @Query("UPDATE MeetingEntity SET syncStatus = :status")
    suspend fun updateAllMeetingSyncStatus(status: String)

    @Transaction
    suspend fun updateMeetings(meetings: List<MeetingEntity>) {
        clearMeetings()
        insertMeetings(meetings = meetings)
    }
}