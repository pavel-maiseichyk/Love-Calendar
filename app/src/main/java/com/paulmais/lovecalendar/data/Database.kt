package com.paulmais.lovecalendar.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [(MeetingEntity::class)], version = 1)
abstract class MeetingsDB : RoomDatabase() {
    abstract fun getMeetingsDao(): MeetingsDao
}