package com.paulmais.lovecalendar.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.paulmais.lovecalendar.presentation.ui.theme.LoveCalendarTheme
import com.paulmais.lovecalendar.presentation.ui.theme.montserrat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    text: String,
    leftButtonVector: ImageVector? = null,
    onLeftButtonClick: () -> Unit = {},
    rightButtonVector: ImageVector? = null,
    onRightButtonClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = text,
                fontFamily = montserrat,
                fontSize = 28.sp
            )
        },
        navigationIcon = {
            leftButtonVector?.let { icon ->
                IconButton(
                    onClick = onLeftButtonClick,
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    )
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null
                    )
                }
            }
        },
        actions = {
            rightButtonVector?.let { icon ->
                IconButton(
                    onClick = onRightButtonClick,
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    )
                ) {
                    Icon(
                        imageVector = icon,
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
        TopAppBar(
            text = "4 Days Left",
            rightButtonVector = Icons.Default.Add
        )
    }
}