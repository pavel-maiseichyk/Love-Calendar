package com.paulmais.lovecalendar.domain.use_case

class FindMonthEmptyDatesAmount {

    fun execute(
        monthFirstDayOfWeekPosition: Int,
        monthDatesSize: Int
    ): Int {
        return 7 - ((monthFirstDayOfWeekPosition + monthDatesSize) % 7)
    }
}