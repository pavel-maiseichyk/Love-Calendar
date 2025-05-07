package com.paulmais.lovecalendar.calendar.presentation.settings.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paulmais.lovecalendar.core.presentation.ui.theme.LoveCalendarTheme
import com.paulmais.lovecalendar.core.presentation.ui.theme.dark_gray
import com.paulmais.lovecalendar.core.presentation.ui.theme.jakarta
import com.paulmais.lovecalendar.core.presentation.ui.theme.medium_gray

@Composable
fun SettingsButton(
    text: String,
    containerColor: Color,
    contentColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        modifier = modifier.height(40.dp),
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
        )
    ) {
        Text(
            textAlign = TextAlign.Center,
            text = text,
            fontSize = 16.sp,
            fontFamily = jakarta,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Preview
@Composable
private fun SettingsButtonPreview() {
    LoveCalendarTheme {
        SettingsButton(
            modifier = Modifier.width(150.dp),
            text = "Cancel",
            containerColor = medium_gray,
            contentColor = dark_gray,
            onClick = {}
        )
    }
}