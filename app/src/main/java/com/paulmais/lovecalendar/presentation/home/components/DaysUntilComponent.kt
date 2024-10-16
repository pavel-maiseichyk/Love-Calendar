package com.paulmais.lovecalendar.presentation.home.components

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paulmais.lovecalendar.domain.model.DaysUntilType.Meeting
import com.paulmais.lovecalendar.domain.model.DaysUntilType.Other
import com.paulmais.lovecalendar.domain.model.DaysUntilType.Special
import com.paulmais.lovecalendar.presentation.ui.theme.Cornflower_Lilac
import com.paulmais.lovecalendar.presentation.ui.theme.LoveCalendarTheme
import com.paulmais.lovecalendar.presentation.ui.theme.Sidecar
import com.paulmais.lovecalendar.presentation.ui.theme.Sindbad
import com.paulmais.lovecalendar.presentation.ui.theme.montserrat

@Composable
fun DaysUntilComponent(
    daysUntilItem: DaysUntilItemUI,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val background = remember {
        if (daysUntilItem.daysUntil == "0") Sidecar
        else when (daysUntilItem.type) {
            Meeting -> Sindbad
            Special -> Cornflower_Lilac
            Other -> Color(0xFFFBFBFB)
        }
    }

    val daysText = when {
        daysUntilItem.isShowingDate -> daysUntilItem.date
        !daysUntilItem.isShowingDate && daysUntilItem.daysUntil == "0" -> "TODAY"
        else -> daysUntilItem.daysUntil
    }

    val textStyle = TextStyle(
        color = MaterialTheme.colorScheme.onSurface,
        fontSize = 24.sp,
        fontFamily = montserrat
    )

    val daysTextStyle = when {
        daysUntilItem.isShowingDate || daysUntilItem.daysUntil == "0" -> textStyle.copy(fontSize = 22.sp)
        else -> textStyle
    }

    Box(
        modifier = modifier
            .clickable(onClick = onClick)
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(background)
            .border(
                border = BorderStroke(1.dp, Color.Black), shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 20.dp, vertical = 12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = daysUntilItem.title,
                style = textStyle
            )
            Text(
                text = daysText,
                style = daysTextStyle
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
                date = "20.02.2022",
                type = Other,
                isShowingDate = false,
            ),
            onClick = {}
        )
    }
}