package com.paulmais.lovecalendar.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface MeetingsDao {

    @Query("SELECT * FROM MeetingEntity")
    fun getMeetings(): Flow<List<MeetingEntity>>

    @Query("DELETE FROM MeetingEntity")
    suspend fun removeAllMeetings()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeetings(meetings: List<MeetingEntity>)

    @Transaction
    suspend fun updateMeetings(meetings: List<MeetingEntity>) {
        removeAllMeetings()
        insertMeetings(meetings = meetings)
    }
}