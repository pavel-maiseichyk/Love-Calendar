package com.example.love_calendar.presentation.home

import com.example.love_calendar.domain.model.AppDate
import com.example.love_calendar.domain.model.Meeting
import kotlinx.datetime.Month

data class HomeState(
    val firstMonth: Month? = null,
    val secondMonth: Month? = null,
    val firstYear: Int = 0,
    val secondYear: Int = 0,
    val firstMonthDates: List<AppDate> = emptyList(),
    val secondMonthDates: List<AppDate> = emptyList(),
    val firstMonthFirstDayOfWeekPosition: Int = 0,
    val secondMonthFirstDayOfWeekPosition: Int = 0,
    val firstMonthEmptyDatesAmount: Int = 0,
    val secondMonthEmptyDatesAmount: Int = 0,
    val isInEditMode: Boolean = false,
    val daysLeftText: String = "",
    val allMeetings: List<Meeting> = emptyList(),
    val isLoading: Boolean = true
)

