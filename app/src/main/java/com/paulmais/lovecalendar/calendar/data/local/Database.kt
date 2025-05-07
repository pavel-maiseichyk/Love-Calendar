package com.paulmais.lovecalendar.calendar.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.paulmais.lovecalendar.calendar.data.local.dao.MeetingsDao
import com.paulmais.lovecalendar.calendar.data.local.entity.MeetingEntity

@Database(entities = [(MeetingEntity::class)], version = 1)
abstract class MeetingsDB : RoomDatabase() {
    abstract fun getMeetingsDao(): MeetingsDao
}