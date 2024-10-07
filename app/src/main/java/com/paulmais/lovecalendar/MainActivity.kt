package com.paulmais.lovecalendar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.paulmais.lovecalendar.di.appModule
import com.paulmais.lovecalendar.presentation.components.CalendarScreen
import com.paulmais.lovecalendar.presentation.components.HomeScreen
import com.paulmais.lovecalendar.presentation.components.MyNavigationBar
import com.paulmais.lovecalendar.presentation.components.SettingsScreen
import com.paulmais.lovecalendar.presentation.home.HomeScreenRoot
import com.paulmais.lovecalendar.presentation.ui.theme.LoveCalendarTheme
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startKoin {
            androidContext(this@MainActivity)
            modules(appModule)
        }

        setContent {
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
                        composable<HomeScreen> { HomeScreenRoot() }
                        composable<CalendarScreen> { }
                        composable<SettingsScreen> { }
                    }
                }
            }
        }
    }
}