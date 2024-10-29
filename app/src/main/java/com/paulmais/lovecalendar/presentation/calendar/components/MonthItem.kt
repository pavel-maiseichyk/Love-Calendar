package com.paulmais.lovecalendar.presentation.calendar.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paulmais.lovecalendar.presentation.calendar.AppDateUI
import com.paulmais.lovecalendar.presentation.ui.theme.montserrat
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month

@Composable
fun MonthItem(
    isEditing: Boolean,
    isCurrent: Boolean,
    month: String,
    year: String,
    firstDayPosition: Int,
    emptyDatesAmount: Int,
    dates: List<AppDateUI>,
    onDateTap: (AppDateUI) -> Unit,
    dayItemSize: Dp,
    modifier: Modifier = Modifier
) {
    val gridHeight = remember(emptyDatesAmount, dates.size, dayItemSize) {
        calculateGridHeight(
            emptyDatesAmount = emptyDatesAmount,
            datesAmount = dates.size,
            daySize = dayItemSize
        )
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.surface)
            .border(
                border = BorderStroke(1.dp, Color.Black), shape = RoundedCornerShape(24.dp)
            )
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp, bottom = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = month, fontFamily = montserrat, fontSize = 28.sp
            )
            Text(
                text = year, fontFamily = montserrat, fontSize = 28.sp
            )
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier
                .fillMaxWidth()
                .height(gridHeight),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            userScrollEnabled = false
        ) {
            items(count = firstDayPosition, key = { it }) {
                if (isCurrent) {
                    DayBeforeItem(
                        size = dayItemSize
                    )
                } else {
                    DayAfterItem(size = dayItemSize)
                }
            }
            items(items = dates, key = { it.date.toString() }) { date ->
                DayItem(
                    onClick = { onDateTap(date) },
                    text = date.date.dayOfMonth.toString(),
                    appDateUI = date,
                    isClickable = isEditing,
                    size = dayItemSize
                )
            }
            items(count = emptyDatesAmount, key = { dates.size - it }) {
                DayAfterItem(
                    size = dayItemSize
                )
            }
        }
    }
}

private fun calculateGridHeight(
    emptyDatesAmount: Int,
    datesAmount: Int,
    padding: Dp = 8.dp,
    daySize: Dp
): Dp {
    val rowAmount = (emptyDatesAmount + datesAmount + 6) / 7
    return (daySize + padding) * rowAmount
}

@Preview
@Composable
private fun MonthItemPreview() {
    MonthItem(
        isEditing = false,
        isCurrent = true,
        month = Month(1).toString(),
        year = "2020",
        firstDayPosition = 2,
        emptyDatesAmount = 2,
        dates = (1..31).map { day ->
            AppDateUI(
                LocalDate(
                    year = 2020, monthNumber = 1, dayOfMonth = day
                )
            )
        },
        onDateTap = {},
        dayItemSize = 20.dp
    )
}