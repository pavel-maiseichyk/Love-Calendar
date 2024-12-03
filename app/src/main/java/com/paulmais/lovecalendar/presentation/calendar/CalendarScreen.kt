package com.paulmais.lovecalendar.presentation.calendar

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.paulmais.lovecalendar.domain.util.DateUtil
import com.paulmais.lovecalendar.presentation.calendar.components.CalendarTopBar
import com.paulmais.lovecalendar.presentation.calendar.components.ChangeMonthButton
import com.paulmais.lovecalendar.presentation.calendar.components.ChangeMonthButtonType
import com.paulmais.lovecalendar.presentation.calendar.components.DaysUntilComponent
import com.paulmais.lovecalendar.presentation.calendar.components.MonthItem
import com.paulmais.lovecalendar.presentation.ui.theme.LoveCalendarTheme
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun CalendarScreenRoot(
    viewModel: CalendarViewModel = koinViewModel(),
    onSettingsClick: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    CalendarScreen(
        state = state,
        onAction = { action ->
            when (action) {
                CalendarAction.OnSettingsClick -> onSettingsClick()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
private fun CalendarScreen(
    state: CalendarState,
    onAction: (CalendarAction) -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val dayItemSize = remember { calculateDaySize(base = screenWidth) }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            CalendarTopBar(
                text = if (state.isInEditMode) "Editing..." else state.daysLeftText,
                leftButtonIcon = if (state.isInEditMode) {
                    painterResource(id = R.drawable.close)
                } else painterResource(id = R.drawable.settings),
                onLeftButtonClick = {
                    if (state.isInEditMode) {
                        onAction(CalendarAction.OnUndoEditClick)
                    } else onAction(CalendarAction.OnSettingsClick)
                },
                rightButtonIcon = if (state.isInEditMode) {
                    painterResource(id = R.drawable.check)
                } else painterResource(id = R.drawable.add),
                onRightButtonClick = {
                    if (state.isInEditMode) {
                        onAction(CalendarAction.OnConfirmEditClick)
                    } else onAction(CalendarAction.OnEditClick)
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Color.White),
            state = listState,
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Column(
                    modifier = Modifier.padding(bottom = 12.dp)
                ) {
                    AnimatedVisibility(
                        visible = !state.isInEditMode
                    ) {
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
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    AnimatedContent(
                        targetState = DateUtil.localDateAtStartOfMonth(
                            month = state.month,
                            year = state.year
                        ),
                        label = "Month Data"
                    ) { date ->
                        MonthItem(
                            isEditing = state.isInEditMode,
                            month = date.month.name,
                            year = date.year.toString(),
                            firstDayPosition = state.firstDayOfWeekPosition,
                            dates = state.appDateUIList,
                            onDateTap = { onAction(CalendarAction.OnDateTap(it)) },
                            dayItemSize = dayItemSize
                        )
                    }
                }
            }
            if (!state.isInEditMode) {
                items(
                    items = state.daysUntilUIList,
                    key = { it.title }
                ) { item ->
                    DaysUntilComponent(
                        modifier = Modifier.animateItem(),
                        daysUntilItem = item,
                        onClick = {
                            coroutineScope.launch {
                                onAction(CalendarAction.OnDaysUntilComponentClick(item.date))
                                listState.animateScrollToItem(0)
                            }
                        }
                    )
                }
            }
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