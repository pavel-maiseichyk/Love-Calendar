package com.paulmais.lovecalendar.presentation.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.paulmais.lovecalendar.R
import com.paulmais.lovecalendar.presentation.calendar.components.ChangeMonthButton
import com.paulmais.lovecalendar.presentation.calendar.components.ChangeMonthButtonType
import com.paulmais.lovecalendar.presentation.components.MyTopBar
import com.paulmais.lovecalendar.presentation.calendar.components.MonthItem
import com.paulmais.lovecalendar.presentation.ui.theme.LoveCalendarTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun CalendarScreenRoot(
    viewModel: CalendarViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    CalendarScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
private fun CalendarScreen(
    state: CalendarState,
    onAction: (CalendarAction) -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val dayItemSize = remember { calculateDaySize(base = screenWidth) }

    Scaffold(
        topBar = {
            MyTopBar(
                text = if (state.isInEditMode) "Editing..." else state.daysLeftText,
                leftButtonPainter = if (state.isInEditMode) painterResource(id = R.drawable.close) else null,
                onLeftButtonClick = { onAction(CalendarAction.OnUndoEditClick) },
                rightButtonPainter = if (state.isInEditMode) painterResource(id = R.drawable.done)
                else painterResource(id = R.drawable.add),
                onRightButtonClick = {
                    if (state.isInEditMode) onAction(CalendarAction.OnConfirmEditClick)
                    else onAction(CalendarAction.OnEditClick)
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Color.White)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ChangeMonthButton(
                    type = ChangeMonthButtonType.PREVIOUS,
                    modifier = Modifier.weight(1f),
                    onClick = { onAction(CalendarAction.OnPreviousClick) }
                )
                ChangeMonthButton(
                    type = ChangeMonthButtonType.NEXT,
                    modifier = Modifier.weight(1f),
                    onClick = { onAction(CalendarAction.OnNextClick) }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            MonthItem(
                isEditing = state.isInEditMode,
                month = state.firstMonthData.month.name,
                year = state.firstMonthData.year.toString(),
                firstDayPosition = state.firstMonthData.firstDayOfWeekPosition,
                dates = state.firstMonthData.dates,
                onDateTap = { onAction(CalendarAction.OnDateTap(it)) },
                dayItemSize = dayItemSize
            )
        }
    }
}

private fun calculateDaySize(base: Dp): Dp {
    return (base - 16.dp * 4 - 1.dp * 2 - 8.dp * 6) / 7
}

@Preview
@Composable
private fun CalendarScreenPreview() {
    LoveCalendarTheme {
        CalendarScreen(
            state = CalendarState(),
            onAction = {}
        )
    }
}