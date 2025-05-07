package com.paulmais.lovecalendar.calendar.presentation.calendar.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paulmais.lovecalendar.R
import com.paulmais.lovecalendar.core.presentation.ui.theme.blue
import com.paulmais.lovecalendar.core.presentation.ui.theme.blue_container
import com.paulmais.lovecalendar.core.presentation.ui.theme.green
import com.paulmais.lovecalendar.core.presentation.ui.theme.green_container
import com.paulmais.lovecalendar.core.presentation.ui.theme.jakarta

enum class ChangeMonthButtonType {
    PREVIOUS, NEXT
}

@Composable
fun ChangeMonthButton(
    modifier: Modifier = Modifier,
    type: ChangeMonthButtonType,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(48.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = when (type) {
                ChangeMonthButtonType.PREVIOUS -> blue_container
                ChangeMonthButtonType.NEXT -> green_container
            },
            contentColor = when (type) {
                ChangeMonthButtonType.PREVIOUS -> blue
                ChangeMonthButtonType.NEXT -> green
            }
        )
    ) {
        Text(
            text = when (type) {
                ChangeMonthButtonType.PREVIOUS -> stringResource(R.string.previous)
                ChangeMonthButtonType.NEXT -> stringResource(R.string.next)
            },
            fontFamily = jakarta,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        )
    }
}

@Preview
@Composable
private fun ChangeMonthButtonPreview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ChangeMonthButton(
            modifier = Modifier.width(173.dp),
            type = ChangeMonthButtonType.PREVIOUS,
            onClick = {}
        )
        ChangeMonthButton(
            modifier = Modifier.width(173.dp),
            type = ChangeMonthButtonType.NEXT,
            onClick = {}
        )
    }
}