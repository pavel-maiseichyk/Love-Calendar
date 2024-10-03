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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        MonthItem(
            isEditing = state.isInEditMode,
            isCurrent = true,
            month = state.firstMonth?.name ?: "",
            year = state.firstYear.toString(),
            firstDayPosition = state.firstMonthFirstDayOfWeekPosition,
            emptyDatesAmount = state.firstMonthEmptyDatesAmount,
            dates = state.firstMonthDates,
            onDateTap = { onAction(HomeAction.OnDateTap(it)) },
        )
        Spacer(modifier = Modifier.height(16.dp))
        MonthItem(
            isEditing = state.isInEditMode,
            isCurrent = false,
            month = state.secondMonth?.name ?: "",
            year = state.secondYear.toString(),
            firstDayPosition = state.secondMonthFirstDayOfWeekPosition,
            emptyDatesAmount = state.secondMonthEmptyDatesAmount,
            dates = state.secondMonthDates,
            onDateTap = { onAction(HomeAction.OnDateTap(it)) },
        )
        Spacer(modifier = Modifier.height(16.dp))
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