package com.paulmais.lovecalendar.presentation.calendar

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paulmais.lovecalendar.domain.model.DateType.*
import com.paulmais.lovecalendar.domain.repository.MeetingsDataSource
import com.paulmais.lovecalendar.domain.use_case.GenerateDates
import com.paulmais.lovecalendar.domain.util.DateUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.daysUntil
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.plus

class CalendarViewModel(
    private val generateDates: GenerateDates,
    private val meetingsDataSource: MeetingsDataSource
) : ViewModel() {

    private var editedMeetings = mutableStateListOf<LocalDate>()

    private val _state = MutableStateFlow(CalendarState())
    val state = _state.asStateFlow()

    init {
        combine(
            meetingsDataSource.getMeetings(),
            meetingsDataSource.getStartingDate()
        ) { meetings, specialDayNumber ->
            reloadHomeState(
                meetings = meetings,
                specialDayNumber = specialDayNumber?.dayOfMonth ?: 0
            )
        }.launchIn(viewModelScope)
    }

    fun onAction(action: CalendarAction) {
        when (action) {
            CalendarAction.OnConfirmEditClick -> {
                viewModelScope.launch {
                    meetingsDataSource.updateMeetings(editedMeetings)
                    editedMeetings.clear()
                    _state.update { it.copy(isInEditMode = false) }
                }
            }

            is CalendarAction.OnDateTap -> {
                if (!state.value.isInEditMode) return

                when (action.appDate.type) {
                    TODAY_MEETING, PAST_MEETING, FUTURE_MEETING, SPECIAL_MEETING, TODAY_MEETING_SPECIAL -> {
                        editedMeetings.remove(action.appDate.date)
                    }

                    else -> editedMeetings.add(action.appDate.date)
                }
                updateDates(editedMeetings)
            }

            CalendarAction.OnEditClick -> {
                editedMeetings.addAll(state.value.meetings)
                _state.update { it.copy(isInEditMode = true) }
            }

            CalendarAction.OnUndoEditClick -> {
                editedMeetings.clear()
                updateDates(state.value.meetings)
                _state.update { it.copy(isInEditMode = false) }
            }
        }
    }

    private fun updateDates(
        meetings: List<LocalDate>
    ) {
        _state.update {
            val now = DateUtil.now()
            val firstMonthDates = generateDates.execute(
                now = now,
                month = now.month,
                year = now.year,
                meetings = meetings,
                specialDayNumber = state.value.specialDayNumber
            )
            val secondMonthDates = generateDates.execute(
                now = now,
                month = now.plus(DatePeriod(months = 1)).month,
                year = now.plus(DatePeriod(months = 1)).year,
                meetings = meetings,
                specialDayNumber = state.value.specialDayNumber
            )

            it.copy(
                firstMonthData = state.value.firstMonthData.copy(dates = firstMonthDates),
                secondMonthData = state.value.secondMonthData.copy(dates = secondMonthDates)
            )
        }
    }

    private fun reloadHomeState(
        meetings: List<LocalDate>,
        specialDayNumber: Int
    ) {
        val now = DateUtil.now()
        val firstMonthData = calculateMonthData(
            now = now,
            meetings = meetings,
            monthsOffset = 0,
            specialDayNumber = specialDayNumber
        )
        val secondMonthData = calculateMonthData(
            now = now,
            meetings = meetings,
            monthsOffset = 1,
            specialDayNumber = specialDayNumber
        )
        val daysLeftText = meetings.sorted().find { it >= now }
            ?.let { createDaysLeftText(now = now, nextMeeting = it) } ?: "None"

        _state.update {
            it.copy(
                firstMonthData = firstMonthData,
                secondMonthData = secondMonthData,
                meetings = meetings,
                daysLeftText = daysLeftText,
                specialDayNumber = specialDayNumber
            )
        }
    }

    private fun calculateMonthData(
        now: LocalDate,
        meetings: List<LocalDate>,
        monthsOffset: Int,
        specialDayNumber: Int
    ): MonthData {
        val targetDate = now.plus(DatePeriod(months = monthsOffset))
        val month = targetDate.month
        val year = targetDate.year
        val monthDates = generateDates.execute(
            now = now,
            month = month,
            year = year,
            meetings = meetings,
            specialDayNumber = specialDayNumber
        )
        val firstDayOfWeekPosition = findFirstDayOfMonthPosition(
            month = month, year = year
        )
        val emptyDatesAmount = findMonthEmptyDatesAmount(
            firstDayOfWeekPosition = firstDayOfWeekPosition,
            datesSize = monthDates.size
        )

        return MonthData(
            month = month,
            year = year,
            dates = monthDates,
            firstDayOfWeekPosition = firstDayOfWeekPosition,
            emptyDatesAmount = emptyDatesAmount
        )
    }


    private fun createDaysLeftText(
        now: LocalDate,
        nextMeeting: LocalDate
    ): String {
        return when (val dateDiff = now.daysUntil(nextMeeting)) {
            0 -> "Today"
            1 -> "1 Day Left"
            else -> "$dateDiff Days Left"
        }
    }

    private fun findFirstDayOfMonthPosition(
        year: Int,
        month: Month
    ): Int {
        return LocalDate(year = year, month = month, dayOfMonth = 1).dayOfWeek.isoDayNumber - 1
    }

    private fun findMonthEmptyDatesAmount(
        firstDayOfWeekPosition: Int,
        datesSize: Int
    ): Int {
        return 7 - ((firstDayOfWeekPosition + datesSize) % 7)
    }
}