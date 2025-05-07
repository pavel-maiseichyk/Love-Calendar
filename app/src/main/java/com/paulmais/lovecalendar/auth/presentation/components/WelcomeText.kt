package com.paulmais.lovecalendar.auth.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paulmais.lovecalendar.R
import com.paulmais.lovecalendar.core.presentation.ui.theme.dark_gray
import com.paulmais.lovecalendar.core.presentation.ui.theme.jakarta

@Composable
fun WelcomeText(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = stringResource(R.string.welcome_to),
            fontSize = 28.sp,
            color = dark_gray,
            fontWeight = FontWeight.Normal,
            fontFamily = jakarta
        )
        Text(
            text = stringResource(R.string.app_name),
            fontSize = 36.sp,
            color = dark_gray,
            fontWeight = FontWeight.SemiBold,
            fontFamily = jakarta
        )
    }
}

@Preview
@Composable
private fun WelcomeTextPreview() {
    WelcomeText()
}