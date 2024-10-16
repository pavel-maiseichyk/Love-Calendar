package com.paulmais.lovecalendar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.paulmais.lovecalendar.di.appModule
import com.paulmais.lovecalendar.presentation.components.CalendarScreen
import com.paulmais.lovecalendar.presentation.components.HomeScreen
import com.paulmais.lovecalendar.presentation.components.MyNavigationBar
import com.paulmais.lovecalendar.presentation.components.SettingsScreen
import com.paulmais.lovecalendar.presentation.calendar.CalendarScreenRoot
import com.paulmais.lovecalendar.presentation.calendar.CalendarViewModel
import com.paulmais.lovecalendar.presentation.home.HomeScreenRoot
import com.paulmais.lovecalendar.presentation.home.HomeViewModel
import com.paulmais.lovecalendar.presentation.settings.SettingsScreenRoot
import com.paulmais.lovecalendar.presentation.ui.theme.LoveCalendarTheme
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startKoin {
            androidContext(this@MainActivity)
            modules(appModule)
        }

        val homeScreenViewModel = getViewModel<HomeViewModel>()
        val calendarViewModel = getViewModel<CalendarViewModel>()

        setContent {
            installSplashScreen().setKeepOnScreenCondition {
                homeScreenViewModel.state.value.daysUntilUIList.isEmpty()
            }

            LoveCalendarTheme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        MyNavigationBar(navController = navController)
                    }
                ) { paddingValues ->
                    NavHost(
                        navController = navController,
                        startDestination = HomeScreen,
                        modifier = Modifier.padding(paddingValues)
                    ) {
                        composable<HomeScreen> { HomeScreenRoot(homeScreenViewModel) }
                        composable<CalendarScreen> { CalendarScreenRoot(calendarViewModel) }
                        composable<SettingsScreen> { SettingsScreenRoot() }
                    }
                }
            }
        }
    }
}