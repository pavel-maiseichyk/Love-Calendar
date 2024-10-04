package com.paulmais.lovecalendar.presentation.home.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paulmais.lovecalendar.presentation.ui.theme.LoveCalendarTheme
import com.paulmais.lovecalendar.presentation.ui.theme.montserrat

@Composable
fun DaysLeftItem(
    text: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.primary),
//            .size(HelperButtonSize)
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text.uppercase().trim(),
            textAlign = TextAlign.Center,
            fontFamily = montserrat,
            fontSize = 32.sp,
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 12.dp)
                .animateContentSize()
        )
    }
}

@Preview
@Composable
private fun DaysLeftItemPreview() {
    LoveCalendarTheme {
        DaysLeftItem(text = "24 days")
    }
}