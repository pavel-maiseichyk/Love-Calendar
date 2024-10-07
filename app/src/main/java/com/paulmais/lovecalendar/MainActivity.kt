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
import com.paulmais.lovecalendar.di.appModule
import com.paulmais.lovecalendar.presentation.components.MyNavigationBar
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

        var currentDestinationIndex by mutableIntStateOf(0)

        setContent {
            LoveCalendarTheme {
                Scaffold(
                    bottomBar = {
                        MyNavigationBar(
                            selectedIndex = currentDestinationIndex,
                            onItemClick = { currentDestinationIndex = it }
                        )
                    }
                ) { paddingValues ->
                    HomeScreenRoot(modifier = Modifier.padding(paddingValues))
                }
            }
        }
    }
}