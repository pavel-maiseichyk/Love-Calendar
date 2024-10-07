package com.paulmais.lovecalendar.presentation.components

import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paulmais.lovecalendar.R
import com.paulmais.lovecalendar.presentation.ui.theme.LoveCalendarTheme

val destinations = listOf(
    NavigationItem(
        index = 0,
        title = "Home",
        selectedIcon = R.drawable.home_filled,
        defaultIcon = R.drawable.home_out,
    ),
    NavigationItem(
        index = 1,
        title = "Calendar",
        selectedIcon = R.drawable.calendar_filled,
        defaultIcon = R.drawable.calendar_out,
    ),
    NavigationItem(
        index = 2,
        title = "Settings",
        selectedIcon = R.drawable.settings_filled,
        defaultIcon = R.drawable.settings_out,
    ),
)

@Composable
fun MyNavigationBar(
    selectedIndex: Int,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier
            .height(60.dp)
            .drawBehind {
                val borderSize = 2.dp.toPx()
                val y = 0.dp.toPx()
                drawLine(
                    color = Color.Black,
                    start = Offset(0f, y),
                    end = Offset(size.width, y),
                    strokeWidth = borderSize
                )
            },
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        destinations.forEach { navigationItem ->
            val selected = selectedIndex == navigationItem.index
            NavigationBarItem(
                selected = selected,
                onClick = { onItemClick(navigationItem.index) },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent
                ),
//                label = { Text(text = navigationItem.title, fontFamily = montserrat) },
                icon = {
                    Icon(
                        painter = if (selected) painterResource(navigationItem.selectedIcon)
                        else painterResource(navigationItem.defaultIcon),
                        contentDescription = navigationItem.title
                    )
                }
            )
        }
    }
}


data class NavigationItem(
    val index: Int,
    val title: String,
    val selectedIcon: Int,
    val defaultIcon: Int
)

@Preview
@Composable
private fun NavigationAppBarPreview() {
    LoveCalendarTheme {
        MyNavigationBar(selectedIndex = 0, onItemClick = {})
    }
}