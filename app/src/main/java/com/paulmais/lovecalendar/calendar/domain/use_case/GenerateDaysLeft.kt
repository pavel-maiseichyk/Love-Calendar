package com.paulmais.lovecalendar.calendar.domain.use_case

import com.paulmais.lovecalendar.calendar.domain.model.DaysUntilItem
import com.paulmais.lovecalendar.calendar.domain.model.DaysUntilType
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.daysUntil
import kotlinx.datetime.monthsUntil
import kotlinx.datetime.plus
import kotlinx.datetime.yearsUntil

class GenerateDaysLeft {

    fun execute(
        now: LocalDate,
        startingDate: LocalDate?,
        meetings: List<LocalDate>
    ): List<DaysUntilItem> {
        val result = mutableListOf<DaysUntilItem>()

        result.add(getToday(now = now))
        getNextMeeting(now = now, meetings = meetings)?.let { result.add(it) }

        startingDate?.let {
            result.addAll(
                getMultiplesOf3NotOf12AndNextMonthiversariesFor3Years(
                    now = now,
                    specialDate = it
                )
            )
            result.addAll(getAnniversariesFor3Years(now = now, specialDate = it))
            result.addAll(getMultiplesOf100For3Years(now = now, startingDate = it))
        }

        return result.sortedBy { it.daysUntil }
    }
}

private fun getToday(
    now: LocalDate,
): DaysUntilItem {
    return DaysUntilItem(
        title = "Today",
        daysUntil = 0,
        date = now,
        type = DaysUntilType.Today
    )
}

private fun getNextMeeting(
    now: LocalDate,
    meetings: List<LocalDate>
): DaysUntilItem? {
    val nextMeeting = meetings.sorted().find { it >= now } ?: return null
    return DaysUntilItem(
        title = "Next Meeting",
        daysUntil = now.daysUntil(nextMeeting),
        date = nextMeeting,
        type = DaysUntilType.Meeting
    )
}

private fun getMultiplesOf3NotOf12AndNextMonthiversariesFor3Years(
    now: LocalDate,
    specialDate: LocalDate
): List<DaysUntilItem> {
    val result = mutableListOf<DaysUntilItem>()
    val monthiversaryDateThisMonth = LocalDate(
        year = now.year,
        month = now.month,
        dayOfMonth = specialDate.dayOfMonth
    )

    val closestMonthiversaryDate = if (now.daysUntil(monthiversaryDateThisMonth) < 0) {
        monthiversaryDateThisMonth.plus(DatePeriod(months = 1))
    } else monthiversaryDateThisMonth

    val monthsFromStartUntilClosest = specialDate.monthsUntil(closestMonthiversaryDate)

    val monthsMultipleOf3FromStartUntilClosest =
        (0..2)
            .first { (monthsFromStartUntilClosest + it) % 3 == 0 }
            .let { monthsFromStartUntilClosest + it }
    var nextDate = specialDate.plus(DatePeriod(months = monthsMultipleOf3FromStartUntilClosest))

    if (nextDate != closestMonthiversaryDate && monthsFromStartUntilClosest % 12 != 0) {
        val closestMonthiversaryItem = DaysUntilItem(
            title = getTitleForMonths(monthsFromStartUntilClosest),
            daysUntil = now.daysUntil(closestMonthiversaryDate),
            date = closestMonthiversaryDate,
            type = DaysUntilType.Special
        )
        result.add(closestMonthiversaryItem)
    }

    val threeYearFromNow = now.plus(DatePeriod(years = 3))

    while (nextDate <= threeYearFromNow) {
        val monthsUntil = specialDate.monthsUntil(nextDate)
        if (monthsUntil % 12 != 0) {
            val item = DaysUntilItem(
                title = getTitleForMonths(monthsUntil),
                daysUntil = now.daysUntil(nextDate),
                date = nextDate,
                type = DaysUntilType.Special
            )
            result.add(item)
        }
        nextDate = nextDate.plus(DatePeriod(months = 3))
    }
    return result
}

private fun getAnniversariesFor3Years(
    now: LocalDate,
    specialDate: LocalDate
): List<DaysUntilItem> {
    val result = mutableListOf<DaysUntilItem>()
    val anniversaryDateThisYear = LocalDate(
        year = now.year,
        month = specialDate.month,
        dayOfMonth = specialDate.dayOfMonth
    )

    var nextAnniversaryDate = if (now.daysUntil(anniversaryDateThisYear) < 0) {
        anniversaryDateThisYear.plus(DatePeriod(years = 1))
    } else anniversaryDateThisYear
    val threeYearsFromNow = now.plus(DatePeriod(years = 3))

    while (nextAnniversaryDate <= threeYearsFromNow) {
        val item = DaysUntilItem(
            title = getTitleForYears(specialDate.yearsUntil(nextAnniversaryDate)),
            daysUntil = now.daysUntil(nextAnniversaryDate),
            date = nextAnniversaryDate,
            type = DaysUntilType.Special
        )
        result.add(item)
        nextAnniversaryDate = nextAnniversaryDate.plus(DatePeriod(years = 1))
    }

    return result
}

private fun getMultiplesOf100For3Years(
    now: LocalDate,
    startingDate: LocalDate
): List<DaysUntilItem> {
    val result = mutableListOf<DaysUntilItem>()

    val startDaysDiff = startingDate.daysUntil(now)
    val nextAmountOfDays = startDaysDiff + (100 - startDaysDiff % 100)
    var nextDate = startingDate.plus(DatePeriod(days = nextAmountOfDays))
    val threeYearsFromNow = now.plus(DatePeriod(years = 3))

    while (nextDate <= threeYearsFromNow) {
        val item = DaysUntilItem(
            title = getTitleForDays(startingDate.daysUntil(nextDate)),
            daysUntil = now.daysUntil(nextDate),
            date = nextDate,
            type = DaysUntilType.Other
        )
        result.add(item)
        nextDate = nextDate.plus(DatePeriod(days = 100))
    }
    return result
}

private fun getTitleForDays(daysAmount: Int): String {
    return if (daysAmount == 1) "1 Day" else "$daysAmount Days"
}

private fun getTitleForMonths(monthsAmount: Int): String {
    return if (monthsAmount == 1) "1 Month" else "$monthsAmount Months"
}

private fun getTitleForYears(yearsAmount: Int): String {
    return if (yearsAmount == 1) "1 Year" else "$yearsAmount Years"
}