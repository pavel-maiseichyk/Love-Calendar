package com.paulmais.lovecalendar.presentation.calendar

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paulmais.lovecalendar.domain.model.DateType.*
import com.paulmais.lovecalendar.domain.repository.MeetingsDataSource
import com.paulmais.lovecalendar.domain.use_case.GenerateDates
import com.paulmais.lovecalendar.domain.use_case.GenerateDaysLeft
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
    private val meetingsDataSource: MeetingsDataSource,
    private val generateDaysLeft: GenerateDaysLeft
) : ViewModel() {

    private var selectedDate = mutableStateOf(DateUtil.baseDate)
    private var specialDayNumber = mutableIntStateOf(0)
    private var dbMeetings = mutableStateListOf<LocalDate>()
    private var editedMeetings = mutableStateListOf<LocalDate>()

    private val _state = MutableStateFlow(CalendarState())
    val state = _state
        .onStart {
            val now = DateUtil.now()
            selectedDate.value = DateUtil.localDateAtStartOfMonth(now)
            loadState(now)
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

                updateAppDateUIList(
                    now = DateUtil.now(),
                    meetings = editedMeetings
                )
            }

            CalendarAction.OnEditClick -> {
                editedMeetings.addAll(dbMeetings)
                _state.update { it.copy(isInEditMode = true) }
            }

            CalendarAction.OnUndoEditClick -> {
                editedMeetings.clear()
                updateAppDateUIList(
                    now = DateUtil.now(),
                    meetings = dbMeetings
                )
                _state.update { it.copy(isInEditMode = false) }
            }

            CalendarAction.OnPreviousClick -> {
                selectedDate.value = selectedDate.value.minus(DatePeriod(months = 1))
            }

            CalendarAction.OnNextClick -> {
                selectedDate.value = selectedDate.value.plus(DatePeriod(months = 1))
            }

            is CalendarAction.OnDaysUntilComponentClick -> {
                selectedDate.value = DateUtil.localDateAtStartOfMonth(action.date)
            }

            else -> Unit
        }
    }

    private fun loadState(
        now: LocalDate
    ) {
        combine(
            meetingsDataSource.getMeetings(),
            meetingsDataSource.getStartingDate(),
            snapshotFlow { selectedDate.value }
        ) { meetings, specialDayDate, currentDate ->
            _state.update { it.copy(isLoading = true) }

            specialDayNumber.intValue = specialDayDate?.dayOfMonth ?: 0

            val selectedDate = if (currentDate == DateUtil.baseDate) {
                DateUtil.localDateAtStartOfMonth(now)
            } else currentDate

            dbMeetings.clear()
            dbMeetings.addAll(meetings)

            val appDateUIList = generateDates.execute(
                now = now,
                month = selectedDate.month,
                year = selectedDate.year,
                meetings = dbMeetings,
                specialDayNumber = specialDayNumber.intValue
            ).map { date -> date.toAppDateUI() }

            val daysLeftText = dbMeetings.sorted().find { it >= now }
                ?.let { createDaysLeftText(now = now, nextMeeting = it) } ?: "None"

            val daysUntilUIList = generateDaysLeft.execute(
                now = now,
                startingDate = specialDayDate,
                meetings = dbMeetings
            ).map { it.toDaysUntilItemUI() }

            _state.update {
                it.copy(
                    isLoading = false,
                    daysLeftText = daysLeftText,
                    month = selectedDate.month,
                    year = selectedDate.year,
                    appDateUIList = appDateUIList,
                    firstDayOfWeekPosition = findFirstDayOfMonthPosition(
                        month = selectedDate.month, year = selectedDate.year
                    ),
                    daysUntilUIList = daysUntilUIList
                )
            }
        }.launchIn(viewModelScope)
    }

    private fun updateAppDateUIList(
        now: LocalDate,
        meetings: List<LocalDate>
    ) {
        val appDateUIList = generateDates.execute(
            now = now,
            meetings = meetings,
            specialDayNumber = specialDayNumber.intValue,
            month = selectedDate.value.month,
            year = selectedDate.value.year
        ).map { date -> date.toAppDateUI() }
        _state.update {
            it.copy(appDateUIList = appDateUIList)
        }
    }

    private fun createDaysLeftText(
        now: LocalDate,
        nextMeeting: LocalDate
    ): String {
        return when (val dateDiff = now.daysUntil(nextMeeting)) {
            0 -> "Today"
            1 -> "1 day left..."
            else -> "$dateDiff days left..."
        }
    }

    private fun findFirstDayOfMonthPosition(
        year: Int,
        month: Month
    ): Int {
        return LocalDate(year = year, month = month, dayOfMonth = 1).dayOfWeek.isoDayNumber - 1
    }
}