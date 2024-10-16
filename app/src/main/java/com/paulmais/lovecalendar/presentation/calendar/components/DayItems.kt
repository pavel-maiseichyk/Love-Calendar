package com.paulmais.lovecalendar.presentation.calendar.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paulmais.lovecalendar.R
import com.paulmais.lovecalendar.domain.model.DateType
import com.paulmais.lovecalendar.domain.model.DateType.*
import com.paulmais.lovecalendar.presentation.ui.theme.futureMeetingColor
import com.paulmais.lovecalendar.presentation.ui.theme.montserrat
import com.paulmais.lovecalendar.presentation.ui.theme.pastMeetingColor
import com.paulmais.lovecalendar.presentation.ui.theme.specialColor

@Composable
fun DayItem(
    onClick: () -> Unit,
    text: String,
    type: DateType,
    isClickable: Boolean,
    modifier: Modifier = Modifier,
    size: Dp
) {
    with(MaterialTheme.colorScheme) {
        val backgroundColor = remember(type) {
            when (type) {
                NORMAL -> Color.White
                PAST -> surface
                TODAY, TODAY_MEETING, TODAY_MEETING_SPECIAL, TODAY_SPECIAL -> primary
                PAST_MEETING -> pastMeetingColor
                FUTURE_MEETING -> futureMeetingColor
                SPECIAL, SPECIAL_MEETING -> specialColor
            }
        }
        val mainIndicatorColor = remember(type) {
            if (type == TODAY_MEETING || type == SPECIAL_MEETING || type == TODAY_MEETING_SPECIAL) {
                futureMeetingColor
            } else null
        }

        val additionalIndicatorColor = remember(type) {
            if (type == TODAY_MEETING_SPECIAL || type == TODAY_SPECIAL) specialColor else null
        }

        val border = remember(type) {
            if (type == PAST) null else BorderStroke(1.dp, Color.Black)
        }

        Card(
            colors = CardDefaults.cardColors(
                containerColor = backgroundColor,
                contentColor = onSurface
            ),
            border = border,
            modifier = modifier
                .clip(RoundedCornerShape(12.dp))
                .size(size)
                .clickable(enabled = isClickable, onClick = onClick)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Indicator(
                    indicatorColor = mainIndicatorColor,
                    isMainIndicator = true
                )
                Indicator(
                    indicatorColor = additionalIndicatorColor,
                    isMainIndicator = false
                )

                if (type == PAST || type == PAST_MEETING) {
                    Image(
                        painter = painterResource(id = R.drawable.before_month),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(Color.Black.copy(alpha = 0.7f))
                    )
                }
                Text(
                    text = text,
                    fontFamily = montserrat,
                    fontSize = 20.sp
                )
            }
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

@Composable
fun DayAfterItem(
    modifier: Modifier = Modifier,
    size: Dp
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .size(size)
            .border(
                border = BorderStroke(1.dp, Color.Gray),
                shape = RoundedCornerShape(12.dp)
            )
    )
}

@Composable
fun DayBeforeItem(
    modifier: Modifier = Modifier,
    size: Dp
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .size(size),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.before_month),
            contentDescription = stringResource(R.string.before_month),
            colorFilter = ColorFilter.tint(Color.Black.copy(alpha = 0.3f))
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
                type = type,
                isClickable = false,
                size = 40.dp
            )
        }
    }
}

@Preview
@Composable
fun DayAfterItemPreview() {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.background)
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        DayAfterItem(size = 40.dp)
    }
}

@Preview
@Composable
fun DayBeforeItemPreview() {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.background)
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        DayBeforeItem(size = 40.dp)
    }
}