package com.paulmais.lovecalendar.domain.use_case

import com.paulmais.lovecalendar.domain.repository.MeetingsDataSource
import kotlinx.coroutines.flow.first
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.daysUntil
import kotlinx.datetime.plus

class GenerateDaysLeft(
    private val meetingsDataSource: MeetingsDataSource
) {

    suspend fun execute(
        now: LocalDate,
        startingDate: LocalDate
    ): Map<DateTitle, DaysLeft> {
        val multiplesOf100 = get5NextMultiplesOf100Map(now = now, startingDate = startingDate)
        val nextMeeting = getNextMeetingMap(
            now = now,
            meetings = meetingsDataSource.getMeetings().first()
        )

        return multiplesOf100 + nextMeeting
    }

    private fun getNextMeetingMap(
        now: LocalDate,
        meetings: List<LocalDate>
    ): Map<DateTitle, DaysLeft> {
        val result = mutableMapOf<DateTitle, DaysLeft>()

        val nextMeeting = meetings.sorted().find { it >= now } ?: return emptyMap()
        val daysTillNextMeeting = now.daysUntil(nextMeeting)

        result["Next Meeting"] = daysTillNextMeeting
        return result
    }

    private fun get5NextMultiplesOf100Map(
        now: LocalDate,
        startingDate: LocalDate
    ): Map<DateTitle, DaysLeft> {
        val result = mutableMapOf<DateTitle, DaysLeft>()

        val startDaysDiff = startingDate.daysUntil(now)
        val nextAmountOfDays = startDaysDiff + (100 - startDaysDiff % 100)
        val nextDate = startingDate.plus(DatePeriod(days = nextAmountOfDays))
        val daysLeft = now.daysUntil(nextDate)

        for (i in 0..400 step 100) {
            result["${nextAmountOfDays + i} Days"] = daysLeft + i
        }
        return result
    }

    // Add monthiversarries, anniversaries and fully symmetrical dates
}

typealias DateTitle = String
typealias DaysLeft = Int
