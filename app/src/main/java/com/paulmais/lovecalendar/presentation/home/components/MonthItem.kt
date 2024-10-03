package com.paulmais.lovecalendar.presentation.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paulmais.lovecalendar.domain.model.AppDate
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
    dates: List<AppDate>,
    onDateTap: (AppDate) -> Unit,
    modifier: Modifier = Modifier
) {
    val rows = remember(firstDayPosition, emptyDatesAmount, dates) {
        (List(firstDayPosition) { 0 } + dates + List(emptyDatesAmount) { 0 }).chunked(7)
    }
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val dayItemSize = calculateDaySize(base = screenWidth)

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
        rows.forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                rowItems.forEach { date ->
                    when {
                        isCurrent && date !is AppDate && rowItems == rows[0] -> {
                            DayBeforeItem(
                                size = dayItemSize
                            )
                        }

                        date !is AppDate -> {
                            DayAfterItem(
                                size = dayItemSize
                            )
                        }

                        else -> {
                            DayItem(
                                onClick = { onDateTap(date) },
                                text = date.date.dayOfMonth.toString(),
                                type = date.type,
                                isClickable = isEditing,
                                size = dayItemSize
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

private fun calculateDaySize(base: Dp): Dp {
    return (base - 16.dp * 4 - 1.dp * 2 - 8.dp * 6) / 7
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
            AppDate(
                LocalDate(
                    year = 2020, monthNumber = 1, dayOfMonth = day
                )
            )
        },
        onDateTap = {}
    )
}