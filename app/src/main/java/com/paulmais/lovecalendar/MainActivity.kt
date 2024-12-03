package com.paulmais.lovecalendar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.paulmais.lovecalendar.app.Route
import com.paulmais.lovecalendar.di.appModule
import com.paulmais.lovecalendar.presentation.calendar.CalendarScreenRoot
import com.paulmais.lovecalendar.presentation.calendar.CalendarViewModel
import com.paulmais.lovecalendar.presentation.settings.SettingsScreenRoot
import com.paulmais.lovecalendar.presentation.ui.theme.LoveCalendarTheme
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LoveCalendarTheme {
                val viewModel = getViewModel<CalendarViewModel>()
                val state by viewModel.state.collectAsStateWithLifecycle()

                installSplashScreen().setKeepOnScreenCondition {
                    state.isLoading
                }

                val navController = rememberNavController()
                Scaffold { paddingValues ->
                    NavHost(
                        navController = navController,
                        startDestination = Route.Calendar,
                        modifier = Modifier.padding(paddingValues)
                    ) {
                        composable<Route.Calendar> {
                            CalendarScreenRoot(
                                onSettingsClick = { navController.navigate(Route.Settings) }
                            )
                        }
                        composable<Route.Settings> {
                            SettingsScreenRoot(
                                onBackClick = { navController.navigateUp() }
                            )
                        }
                    }
                }
            }
        }
    }
}