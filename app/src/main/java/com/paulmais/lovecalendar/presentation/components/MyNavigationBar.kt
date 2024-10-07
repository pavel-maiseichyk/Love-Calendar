package com.paulmais.lovecalendar.presentation.components

import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.paulmais.lovecalendar.R
import com.paulmais.lovecalendar.presentation.ui.theme.LoveCalendarTheme
import kotlinx.serialization.Serializable

@Serializable
object HomeScreen

@Serializable
object CalendarScreen

@Serializable
object SettingsScreen

val destinations = listOf(
    NavigationItem(
        route = HomeScreen,
        title = "Home",
        selectedIcon = R.drawable.home_filled,
        defaultIcon = R.drawable.home_out,
    ),
    NavigationItem(
        route = CalendarScreen,
        title = "Calendar",
        selectedIcon = R.drawable.calendar_filled,
        defaultIcon = R.drawable.calendar_out,
    ),
    NavigationItem(
        route = SettingsScreen,
        title = "Settings",
        selectedIcon = R.drawable.settings_filled,
        defaultIcon = R.drawable.settings_out,
    ),
)

@Composable
fun MyNavigationBar(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
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
            val selected =
                currentDestination?.hierarchy?.any { it.hasRoute(navigationItem.route::class) } == true
            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController.navigate(navigationItem.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent
                ),
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
    val route: Any,
    val title: String,
    val selectedIcon: Int,
    val defaultIcon: Int
)

@Preview
@Composable
private fun NavigationAppBarPreview() {
    LoveCalendarTheme {
        MyNavigationBar(navController = rememberNavController())
    }
}