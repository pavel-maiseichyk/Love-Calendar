package com.paulmais.lovecalendar.core.presentation.component

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paulmais.lovecalendar.core.presentation.ui.theme.jakarta
import com.paulmais.lovecalendar.core.presentation.ui.theme.red
import com.paulmais.lovecalendar.core.presentation.ui.theme.red_container

@Composable
fun AppButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(48.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = red_container,
            contentColor = red
        )
    ) {
        Text(
            text = text,
            fontFamily = jakarta,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        )
    }
}