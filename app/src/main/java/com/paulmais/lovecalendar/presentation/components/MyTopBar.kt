package com.paulmais.lovecalendar.presentation.components

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paulmais.lovecalendar.presentation.ui.theme.LoveCalendarTheme
import com.paulmais.lovecalendar.presentation.ui.theme.jakarta

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopBar(
    text: String,
    leftButtonPainter: Painter? = null,
    onLeftButtonClick: () -> Unit = {},
    rightButtonPainter: Painter? = null,
    onRightButtonClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        modifier = modifier
            .drawBehind {
                val borderSize = 2.dp.toPx()
                val y = size.height
                drawLine(
                    color = Color.Black,
                    start = Offset(0f, y),
                    end = Offset(size.width, y),
                    strokeWidth = borderSize
                )
            },
        title = {
            Text(
                text = text,
                fontFamily = jakarta,
                fontSize = 28.sp,
            )
        },
        navigationIcon = {
            leftButtonPainter?.let { icon ->
                IconButton(
                    onClick = onLeftButtonClick,
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    )
                ) {
                    Icon(
                        painter = icon,
                        contentDescription = null
                    )
                }
            }
        },
        actions = {
            rightButtonPainter?.let { icon ->
                IconButton(
                    onClick = onRightButtonClick,
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    )
                ) {
                    Icon(
                        painter = icon,
                        contentDescription = null
                    )
                }
            }
        }
    )
}

@Preview
@Composable
private fun TopAppBarPreview() {
    LoveCalendarTheme {
        MyTopBar(
            text = "4 Days Left",
//            rightButtonVector = Icons.Default.Add
        )
    }
}