package com.paulmais.lovecalendar.presentation.calendar.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paulmais.lovecalendar.presentation.ui.theme.dark_gray
import com.paulmais.lovecalendar.presentation.ui.theme.jakarta
import com.paulmais.lovecalendar.presentation.ui.theme.light_gray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarTopBar(
    text: String,
    leftButtonIcon: Painter,
    onLeftButtonClick: () -> Unit = {},
    rightButtonIcon: Painter,
    onRightButtonClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column {
        TopAppBar(
            title = {
                Text(
                    text = text,
                    fontFamily = jakarta,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                    color = dark_gray
                )
            },
            modifier = modifier,
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White,
                titleContentColor = dark_gray
            ),
            actions = {
                IconButton(
                    onClick = onLeftButtonClick,
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = dark_gray
                    )
                ) {
                    Icon(
                        painter = leftButtonIcon,
                        contentDescription = null
                    )
                }

                IconButton(
                    onClick = onRightButtonClick,
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = dark_gray
                    )
                ) {
                    Icon(
                        painter = rightButtonIcon,
                        contentDescription = null
                    )
                }
            }
        )
        HorizontalDivider(
            thickness = 1.dp,
            color = light_gray
        )
    }
}