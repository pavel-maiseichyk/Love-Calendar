package com.paulmais.lovecalendar.presentation.home

import androidx.lifecycle.ViewModel
import com.paulmais.lovecalendar.domain.model.AppDate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class HomeViewModel : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    init {
        _state.update {
            it.copy(
                firstMonth = Month(1),
                secondMonth = Month(2),
                firstYear = 2020,
                secondYear = 2020,
                firstMonthDates = (1..31).map { day ->
                    AppDate(
                        LocalDate(
                            year = 2020,
                            monthNumber = 1,
                            dayOfMonth = day
                        )
                    )
                },
                secondMonthDates = (1..29).map { day ->
                    AppDate(
                        LocalDate(
                            year = 2020,
                            monthNumber = 1,
                            dayOfMonth = day
                        )
                    )
                },
                firstMonthFirstDayOfWeekPosition = 2,
                secondMonthFirstDayOfWeekPosition = 5,
                firstMonthEmptyDatesAmount = 2,
                secondMonthEmptyDatesAmount = 1,
                isInEditMode = false,
                daysLeftText = "...",
                meetings = emptyList(),
                isLoading = false,
                today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
            )
        }
    }

    fun onAction(action: HomeAction) {
        when (action) {
            HomeAction.OnConfirmEditClick -> TODO()
            is HomeAction.OnDateTap -> TODO()
            HomeAction.OnEditClick -> TODO()
            HomeAction.OnSettingsClick -> TODO()
            HomeAction.OnUndoEditClick -> TODO()
        }
    }
}