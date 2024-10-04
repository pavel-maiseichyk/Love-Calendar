package com.paulmais.lovecalendar.presentation.home

import androidx.lifecycle.ViewModel
import com.paulmais.lovecalendar.domain.model.AppDate
import com.paulmais.lovecalendar.domain.use_case.FindFirstDayOfMonthPosition
import com.paulmais.lovecalendar.domain.use_case.FindMonthEmptyDatesAmount
import com.paulmais.lovecalendar.domain.use_case.GenerateDates
import com.paulmais.lovecalendar.domain.util.DateUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime

class HomeViewModel(
    private val generateDates: GenerateDates,
    private val findFirstDayOfMonthPosition: FindFirstDayOfMonthPosition,
    private val findMonthEmptyDatesAmount: FindMonthEmptyDatesAmount
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    init {
        _state.update {
            val now = DateUtil.now()
            val firstMonth = now.month
            val secondMonth = now.plus(DatePeriod(months = 1)).month
            val firstYear = now.year
            val secondYear = now.plus(DatePeriod(months = 1)).year

            val firstMonthDates = generateDates.execute(
                now = now, month = firstMonth, year = firstYear, meetings = emptyList()
            )
            val secondMonthDates = generateDates.execute(
                now = now, month = secondMonth, year = secondYear, meetings = emptyList()
            )

            val firstMonthFirstDayOfWeekPosition = findFirstDayOfMonthPosition.execute(
                month = firstMonth, year = firstYear
            )
            val secondMonthFirstDayOfWeekPosition = findFirstDayOfMonthPosition.execute(
                month = secondMonth, year = secondYear
            )

            it.copy(
                firstMonth = firstMonth,
                secondMonth = secondMonth,
                firstYear = firstYear,
                secondYear = secondYear,
                firstMonthDates = firstMonthDates,
                secondMonthDates = secondMonthDates,
                firstMonthFirstDayOfWeekPosition = firstMonthFirstDayOfWeekPosition,
                secondMonthFirstDayOfWeekPosition = secondMonthFirstDayOfWeekPosition,
                firstMonthEmptyDatesAmount = findMonthEmptyDatesAmount.execute(
                    monthFirstDayOfWeekPosition = firstMonthFirstDayOfWeekPosition,
                    monthDatesSize = firstMonthDates.size
                ),
                secondMonthEmptyDatesAmount = findMonthEmptyDatesAmount.execute(
                    monthFirstDayOfWeekPosition = secondMonthFirstDayOfWeekPosition,
                    monthDatesSize = secondMonthDates.size
                )
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