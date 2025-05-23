package com.paulmais.lovecalendar.calendar.presentation.calendar.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.paulmais.lovecalendar.calendar.presentation.calendar.models.AppDateUI
import com.paulmais.lovecalendar.core.presentation.ui.theme.dark_gray
import com.paulmais.lovecalendar.core.presentation.ui.theme.jakarta
import com.paulmais.lovecalendar.core.presentation.ui.theme.light_gray
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import java.util.Locale
import kotlin.math.ceil

@Composable
fun MonthItem(
    isEditing: Boolean,
    month: String,
    year: String,
    firstDayPosition: Int,
    dates: List<AppDateUI>,
    onDateTap: (AppDateUI) -> Unit,
    dayItemSize: Dp,
    modifier: Modifier = Modifier
) {
    val gridHeight = remember(dates) {
        calculateGridHeight(
            emptyDatesAmount = firstDayPosition,
            datesAmount = dates.size,
            daySize = dayItemSize
        )
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .border(
                border = BorderStroke(1.dp, light_gray),
                shape = RoundedCornerShape(24.dp)
            )
            .background(Color.White)
            .padding(all = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = month
                    .lowercase()
                    .replaceFirstChar { it.titlecase(Locale.ROOT) },
                fontFamily = jakarta,
                fontSize = 26.sp,
                color = dark_gray
            )
            Text(
                text = year,
                fontFamily = jakarta,
                fontSize = 26.sp,
                color = dark_gray
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
                Box(modifier = Modifier.size(dayItemSize))
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
        }
    }
}

private fun calculateGridHeight(
    emptyDatesAmount: Int,
    datesAmount: Int,
    padding: Dp = 8.dp,
    daySize: Dp
): Dp {
    val rowAmount = ceil((emptyDatesAmount + datesAmount) / 7f).toInt()
    return daySize * rowAmount + padding * (rowAmount - 1)
}

@Preview
@Composable
private fun MonthItemPreview() {
    MonthItem(
        modifier = Modifier.width(360.dp),
        isEditing = false,
        month = Month(1).toString(),
        year = "2020",
        firstDayPosition = 2,
        dates = (1..31).map { day ->
            AppDateUI(
                LocalDate(
                    year = 2020, monthNumber = 1, dayOfMonth = day
                )
            )
        },
        onDateTap = {},
        dayItemSize = 40.dp
    )
}