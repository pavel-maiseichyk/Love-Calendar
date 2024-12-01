package com.paulmais.lovecalendar.presentation.calendar

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paulmais.lovecalendar.domain.model.DateType.*
import com.paulmais.lovecalendar.domain.repository.MeetingsDataSource
import com.paulmais.lovecalendar.domain.use_case.GenerateDates
import com.paulmais.lovecalendar.domain.util.DateUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.daysUntil
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.minus
import kotlinx.datetime.plus

class CalendarViewModel(
    private val generateDates: GenerateDates,
    private val meetingsDataSource: MeetingsDataSource
) : ViewModel() {

    private var editedMeetings = mutableStateListOf<LocalDate>()
    private var selectedDate = mutableStateOf(LocalDate(year = 0, monthNumber = 1, dayOfMonth = 1))

    private val _state = MutableStateFlow(CalendarState())
    val state = _state
        .onStart {
            selectedDate.value = DateUtil.nowWithFirstDayOfMonth() // Using first day of month for easier movements between months

            combine(
                meetingsDataSource.getMeetings(),
                meetingsDataSource.getStartingDate(),
                snapshotFlow { selectedDate.value }
            ) { meetings, specialDayNumber, date ->
                reloadHomeState(
                    now = DateUtil.now(),
                    month = date.month,
                    year = date.year,
                    meetings = meetings,
                    specialDayNumber = specialDayNumber?.dayOfMonth ?: 0
                )
            }.launchIn(viewModelScope)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = _state.value
        )

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

                if (MEETING in action.appDate.types) {
                    editedMeetings.remove(action.appDate.date)
                } else {
                    editedMeetings.add(action.appDate.date)
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

            CalendarAction.OnPreviousClick -> {
                selectedDate.value = selectedDate.value.minus(DatePeriod(months = 1))
            }
            CalendarAction.OnNextClick -> {
                selectedDate.value = selectedDate.value.plus(DatePeriod(months = 1))
            }
            CalendarAction.OnSettingsClick -> {

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
            ).map { date -> date.toAppDateUI() }
            val secondMonthDates = generateDates.execute(
                now = now,
                month = now.plus(DatePeriod(months = 1)).month,
                year = now.plus(DatePeriod(months = 1)).year,
                meetings = meetings,
                specialDayNumber = state.value.specialDayNumber
            ).map { date -> date.toAppDateUI() }

            it.copy(
                firstMonthData = state.value.firstMonthData.copy(dates = firstMonthDates),
                secondMonthData = state.value.secondMonthData.copy(dates = secondMonthDates)
            )
        }
    }

    private fun reloadHomeState(
        now: LocalDate,
        month: Month,
        year: Int,
        meetings: List<LocalDate>,
        specialDayNumber: Int
    ) {
        val firstMonthData = calculateMonthData(
            month = month,
            year = year,
            meetings = meetings,
            specialDayNumber = specialDayNumber
        )
        val daysLeftText = meetings.sorted().find { it >= now }
            ?.let { createDaysLeftText(now = now, nextMeeting = it) } ?: "None"

        _state.update {
            it.copy(
                firstMonthData = firstMonthData,
                meetings = meetings,
                daysLeftText = daysLeftText,
                specialDayNumber = specialDayNumber
            )
        }
    }

    private fun calculateMonthData(
        month: Month,
        year: Int,
        meetings: List<LocalDate>,
        specialDayNumber: Int
    ): MonthData {
        val now = DateUtil.now()
        val monthDates = generateDates.execute(
            now = now,
            month = month,
            year = year,
            meetings = meetings,
            specialDayNumber = specialDayNumber
        ).map { date -> date.toAppDateUI() }
        val firstDayOfWeekPosition = findFirstDayOfMonthPosition(
            month = month, year = year
        )

        return MonthData(
            month = month,
            year = year,
            dates = monthDates,
            firstDayOfWeekPosition = firstDayOfWeekPosition,
        )
    }


    private fun createDaysLeftText(
        now: LocalDate,
        nextMeeting: LocalDate
    ): String {
        return when (val dateDiff = now.daysUntil(nextMeeting)) {
            0 -> "Today"
            1 -> "1 day left"
            else -> "$dateDiff days left"
        }
    }

    private fun findFirstDayOfMonthPosition(
        year: Int,
        month: Month
    ): Int {
        return LocalDate(year = year, month = month, dayOfMonth = 1).dayOfWeek.isoDayNumber - 1
    }
}