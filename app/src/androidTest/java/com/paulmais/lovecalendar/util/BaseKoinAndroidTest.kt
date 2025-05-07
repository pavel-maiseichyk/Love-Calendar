package com.paulmais.lovecalendar.util

import android.content.Context
import android.content.SharedPreferences
import androidx.test.core.app.ApplicationProvider
import com.paulmais.lovecalendar.calendar.data.local.MeetingsDB
import org.junit.After
import org.junit.Before
import org.koin.test.KoinTest
import org.koin.test.inject

abstract class BaseKoinAndroidTest : KoinTest {
    protected lateinit var context: Context

    val db: MeetingsDB by inject()
    val prefs: SharedPreferences by inject()

    @Before
    open fun setUp() {
        context = ApplicationProvider.getApplicationContext()

        db.clearAllTables()
        prefs.edit().clear().commit()
    }

    @After
    open fun tearDown() {
        db.close()
    }
}