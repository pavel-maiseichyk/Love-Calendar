package com.paulmais.lovecalendar.calendar.presentation.calendar.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paulmais.lovecalendar.calendar.domain.model.DateType
import com.paulmais.lovecalendar.calendar.presentation.calendar.models.AppDateUI
import com.paulmais.lovecalendar.core.presentation.ui.theme.jakarta

@Composable
fun DayItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    appDateUI: AppDateUI,
    isClickable: Boolean,
    size: Dp = 40.dp
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = appDateUI.containerColor,
            contentColor = appDateUI.contentColor
        ),
        modifier = modifier
            .clip(CircleShape)
            .size(size)
            .clickable(enabled = isClickable, onClick = onClick)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                fontFamily = jakarta,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
private fun BoxScope.Indicator(
    indicatorColor: Color?,
    isMainIndicator: Boolean,
    modifier: Modifier = Modifier
) {
    if (indicatorColor != null) {
        Box(
            modifier = modifier
                .padding(4.dp)
                .size(8.dp)
                .clip(CircleShape)
                .border(shape = CircleShape, border = BorderStroke(0.75.dp, Color.Black))
                .background(indicatorColor)
                .let {
                    if (isMainIndicator) it.align(Alignment.TopEnd)
                    else it.align(Alignment.TopStart)
                }
        )
    }
}

@Preview
@Composable
fun DayItemPreview() {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.background)
            .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        DateType.entries.forEachIndexed { index, type ->
            DayItem(
                onClick = { },
                text = "${index + 1}",
                appDateUI = AppDateUI(),
                isClickable = false,
                size = 40.dp
            )
        }
    }
}