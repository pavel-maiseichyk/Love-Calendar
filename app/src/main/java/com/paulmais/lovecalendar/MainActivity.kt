package com.paulmais.lovecalendar

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.paulmais.lovecalendar.app.Route
import com.paulmais.lovecalendar.di.appModule
import com.paulmais.lovecalendar.presentation.calendar.CalendarScreenRoot
import com.paulmais.lovecalendar.presentation.calendar.CalendarViewModel
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

        // TODO: replace with KoinApplication(application = { modules(appModule) })
        val calendarScreenViewModel = getViewModel<CalendarViewModel>()

        setContent {
            installSplashScreen().setKeepOnScreenCondition {
                false
//                calendarScreenViewModel.state.value.isLoading
            }

            LoveCalendarTheme {
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
                        composable<Route.Settings> { SettingsScreenRoot() }
                    }
                }
            }
        }
    }
}