package com.paulmais.lovecalendar.calendar.presentation.calendar.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paulmais.lovecalendar.calendar.domain.model.DaysUntilType
import com.paulmais.lovecalendar.calendar.presentation.calendar.models.DaysUntilItemUI
import com.paulmais.lovecalendar.core.presentation.ui.theme.LoveCalendarTheme
import com.paulmais.lovecalendar.core.presentation.ui.theme.dark_gray
import com.paulmais.lovecalendar.core.presentation.ui.theme.green
import com.paulmais.lovecalendar.core.presentation.ui.theme.green_container
import com.paulmais.lovecalendar.core.presentation.ui.theme.jakarta
import com.paulmais.lovecalendar.core.presentation.ui.theme.light_gray
import com.paulmais.lovecalendar.core.presentation.ui.theme.red
import com.paulmais.lovecalendar.core.presentation.ui.theme.red_container
import com.paulmais.lovecalendar.core.presentation.ui.theme.yellow
import com.paulmais.lovecalendar.core.presentation.ui.theme.yellow_container
import kotlinx.datetime.LocalDate

@Composable
fun DaysUntilComponent(
    daysUntilItem: DaysUntilItemUI,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val containerColor = when (daysUntilItem.type) {
        DaysUntilType.Today -> yellow_container
        DaysUntilType.Meeting -> green_container
        DaysUntilType.Special -> red_container
        else -> Color.White
    }

    val contentColor = when (daysUntilItem.type) {
        DaysUntilType.Today -> yellow
        DaysUntilType.Meeting -> green
        DaysUntilType.Special -> red
        else -> dark_gray
    }

    val daysText = when {
        daysUntilItem.type == DaysUntilType.Today -> ""
        else -> daysUntilItem.daysUntil
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .border(
                width = if (daysUntilItem.type == DaysUntilType.Other) 1.dp else 0.dp,
                color = light_gray,
                shape = RoundedCornerShape(16.dp)
            )
            .fillMaxWidth()
            .background(containerColor)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = daysUntilItem.title,
                color = contentColor,
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 20.sp,
                    fontFamily = jakarta
                )
            )
            Text(
                text = daysText,
                color = contentColor,
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 20.sp,
                    fontFamily = jakarta
                )
            )
        }
    }
}

@Preview
@Composable
private fun DaysUntilComponentPreview() {
    LoveCalendarTheme {
        DaysUntilComponent(
            daysUntilItem = DaysUntilItemUI(
                title = "Monthiversary",
                daysUntil = "15",
                date = LocalDate(year = 2022, monthNumber = 2, dayOfMonth = 20),
                type = DaysUntilType.Other,
                isShowingDate = false,
            ),
            onClick = {}
        )
    }
}