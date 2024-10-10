package com.paulmais.lovecalendar.domain.use_case

import com.paulmais.lovecalendar.domain.model.DaysUntilItem
import com.paulmais.lovecalendar.domain.model.DaysUntilType
import com.paulmais.lovecalendar.domain.repository.MeetingsDataSource
import com.paulmais.lovecalendar.domain.util.DateUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.daysUntil
import kotlinx.datetime.monthsUntil
import kotlinx.datetime.plus
import kotlinx.datetime.yearsUntil

class GenerateDaysLeft(
    private val meetingsDataSource: MeetingsDataSource
) {

    suspend fun execute(
        now: LocalDate = DateUtil.now()
    ): Flow<List<DaysUntilItem>> {
        return flow {
            combine(
                meetingsDataSource.getStartingDate(),
                meetingsDataSource.getMeetings()
            ) { startingDate, meetings ->
                val result = mutableListOf<DaysUntilItem>()

                val multiplesOf100Map =
                    startingDate?.let { get5NextMultiplesOf100Map(now = now, startingDate = it) }
                if (multiplesOf100Map != null) result.addAll(multiplesOf100Map)

                val nextMeeting = getNextMeeting(now = now, meetings = meetings)
                if (nextMeeting != null) result.add(nextMeeting)

                val nextAnniversary =
                    startingDate?.let { getNextAnniversary(now = now, specialDate = it) }
                if (nextAnniversary != null) result.add(nextAnniversary)


                startingDate
                    ?.let { getNextMonthiversary(now = now, specialDate = it) }
                    ?.let { result.add(it) }

//                val monthiversaries =
//                    startingDate?.let { getMonthiversariesForYear(now = now, specialDate = it) }
//                if (monthiversaries != null) result.addAll(monthiversaries)

                result.sortedBy { it.daysUntil }
            }.collect { emit(it) }
        }
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

    private fun getNextAnniversary(
        now: LocalDate,
        specialDate: LocalDate
    ): DaysUntilItem {
        val anniversaryDateThisYear = LocalDate(
            year = now.year,
            month = specialDate.month,
            dayOfMonth = specialDate.dayOfMonth
        )

        val nextAnniversaryDate = if (now.daysUntil(anniversaryDateThisYear) < 0) {
            anniversaryDateThisYear.plus(DatePeriod(years = 1))
        } else anniversaryDateThisYear

        val daysUntilNextAnniversary = now.daysUntil(nextAnniversaryDate)
        val yearsTogether = specialDate.yearsUntil(nextAnniversaryDate)
        return DaysUntilItem(
            title = if (yearsTogether > 1) "$yearsTogether years" else "$yearsTogether year",
            daysUntil = daysUntilNextAnniversary,
            type = DaysUntilType.Special
        )
    }

    private fun getNextMonthiversary(
        now: LocalDate,
        specialDate: LocalDate
    ): DaysUntilItem {
        val monthiversaryDateThisMonth = LocalDate(
            year = now.year,
            month = now.month,
            dayOfMonth = specialDate.dayOfMonth
        )

        val nextMonthiversaryDate = if (now.daysUntil(monthiversaryDateThisMonth) < 0) {
            monthiversaryDateThisMonth.plus(DatePeriod(months = 1))
        } else monthiversaryDateThisMonth

        val monthsAmount = specialDate.monthsUntil(nextMonthiversaryDate)

        return DaysUntilItem(
            title = "$monthsAmount Month(s)",
            daysUntil = now.daysUntil(nextMonthiversaryDate),
            type = DaysUntilType.Special
        )
    }

//    private fun getMonthiversariesForYear(
//        now: LocalDate,
//        specialDate: LocalDate
//    ): List<DaysUntilItem> {
//        val result = mutableListOf<DaysUntilItem>()
//        val yearFromNow = now.plus(DatePeriod(years = 1))
//
//        val monthiversaryDateThisMonth = LocalDate(
//            year = now.year,
//            month = now.month,
//            dayOfMonth = specialDate.dayOfMonth
//        )
//
//        var nextMonthiversaryDate = if (now.daysUntil(monthiversaryDateThisMonth) < 0) {
//            monthiversaryDateThisMonth.plus(DatePeriod(months = 1))
//        } else monthiversaryDateThisMonth
//
//        while (nextMonthiversaryDate <= yearFromNow) {
//            val monthsAmount = specialDate.monthsUntil(nextMonthiversaryDate)
//            val item = DaysUntilItem(
//                title = if (monthsAmount > 1) "$monthsAmount months" else "$monthsAmount month",
//                daysUntil = now.daysUntil(nextMonthiversaryDate),
//                type = DaysUntilType.Special
//            )
//            if (monthsAmount % 12 != 0 && monthsAmount % 3 == 0) result.add(item)
//            nextMonthiversaryDate = nextMonthiversaryDate.plus(DatePeriod(months = 1))
//        }
//        return result
//    }
}