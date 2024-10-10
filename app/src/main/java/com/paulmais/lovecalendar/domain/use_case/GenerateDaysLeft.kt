package com.paulmais.lovecalendar.domain.use_case

import com.paulmais.lovecalendar.domain.model.DaysUntilItem
import com.paulmais.lovecalendar.domain.model.DaysUntilType
import com.paulmais.lovecalendar.domain.repository.MeetingsDataSource
import com.paulmais.lovecalendar.domain.util.DateUtil
import kotlinx.coroutines.flow.first
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.daysUntil
import kotlinx.datetime.plus

class GenerateDaysLeft(
    private val meetingsDataSource: MeetingsDataSource
) {

    suspend fun execute(
        now: LocalDate = DateUtil.now()
    ): List<DaysUntilItem> {

        val multiplesOf100 = meetingsDataSource.getStartingDate()
            .first()?.let { date ->
                get5NextMultiplesOf100Map(now = now, startingDate = date)
            } ?: emptyList()

        val nextMeeting = getNextMeeting(
            now = now,
            meetings = meetingsDataSource.getMeetings().first()
        )

        nextMeeting?.let { return multiplesOf100 + it }

        return multiplesOf100
    }

    private fun getNextMeeting(
        now: LocalDate,
        meetings: List<LocalDate>
    ): DaysUntilItem? {
        val nextMeeting = meetings.sorted().find { it >= now } ?: return null
        return DaysUntilItem(
            title = "Next Meeting",
            daysUntil = now.daysUntil(nextMeeting),
            type = DaysUntilType.Meeting
        )
    }

    private fun get5NextMultiplesOf100Map(
        now: LocalDate,
        startingDate: LocalDate
    ): List<DaysUntilItem> {

        val result = mutableListOf<DaysUntilItem>()

        val startDaysDiff = startingDate.daysUntil(now)
        val nextAmountOfDays = startDaysDiff + (100 - startDaysDiff % 100)
        val nextDate = startingDate.plus(DatePeriod(days = nextAmountOfDays))
        val daysLeft = now.daysUntil(nextDate)

        for (i in 0..400 step 100) {
            val item = DaysUntilItem(
                title = "${nextAmountOfDays + i} Days",
                daysUntil = daysLeft + i,
                type = DaysUntilType.Other
            )
            result.add(item)
        }
        return result
    }

    // Add monthiversarries, anniversaries and fully symmetrical dates
}