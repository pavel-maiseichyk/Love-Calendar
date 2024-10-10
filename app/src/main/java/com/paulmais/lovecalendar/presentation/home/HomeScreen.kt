package com.paulmais.lovecalendar.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.paulmais.lovecalendar.R
import com.paulmais.lovecalendar.presentation.components.MyTopBar
import com.paulmais.lovecalendar.presentation.home.components.MonthItem
import com.paulmais.lovecalendar.presentation.ui.theme.LoveCalendarTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreenRoot(
    viewModel: HomeViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    HomeScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
private fun HomeScreen(
    state: HomeState,
    onAction: (HomeAction) -> Unit
) {
    Scaffold(
        topBar = {
            MyTopBar(
                text = if (state.isInEditMode) "Editing..." else state.daysLeftText,
                leftButtonPainter = if (state.isInEditMode) painterResource(id = R.drawable.close) else null,
                onLeftButtonClick = { onAction(HomeAction.OnUndoEditClick) },
                rightButtonPainter = if (state.isInEditMode) painterResource(id = R.drawable.done)
                else painterResource(id = R.drawable.add),
                onRightButtonClick = {
                    if (state.isInEditMode) onAction(HomeAction.OnConfirmEditClick)
                    else onAction(HomeAction.OnEditClick)
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            MonthItem(
                isEditing = state.isInEditMode,
                isCurrent = true,
                month = state.firstMonthData.month.name,
                year = state.firstMonthData.year.toString(),
                firstDayPosition = state.firstMonthData.firstDayOfWeekPosition,
                emptyDatesAmount = state.firstMonthData.emptyDatesAmount,
                dates = state.firstMonthData.dates,
                onDateTap = { onAction(HomeAction.OnDateTap(it)) },
            )
            Spacer(modifier = Modifier.height(16.dp))
            MonthItem(
                isEditing = state.isInEditMode,
                isCurrent = false,
                month = state.secondMonthData.month.name,
                year = state.secondMonthData.year.toString(),
                firstDayPosition = state.secondMonthData.firstDayOfWeekPosition,
                emptyDatesAmount = state.secondMonthData.emptyDatesAmount,
                dates = state.secondMonthData.dates,
                onDateTap = { onAction(HomeAction.OnDateTap(it)) },
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    LoveCalendarTheme {
        HomeScreen(
            state = HomeState(),
            onAction = {}
        )
    }
}