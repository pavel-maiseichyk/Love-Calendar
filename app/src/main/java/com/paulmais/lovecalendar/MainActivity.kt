package com.paulmais.lovecalendar

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.paulmais.lovecalendar.app.Route
import com.paulmais.lovecalendar.auth.presentation.AuthScreenRoot
import com.paulmais.lovecalendar.calendar.presentation.calendar.CalendarScreenRoot
import com.paulmais.lovecalendar.calendar.presentation.settings.SettingsScreenRoot
import com.paulmais.lovecalendar.core.presentation.UiText
import com.paulmais.lovecalendar.core.presentation.ui.theme.LoveCalendarTheme
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.getViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LoveCalendarTheme {
                val context = LocalContext.current
                val navController = rememberNavController()
                val coroutineScope = rememberCoroutineScope()
                val snackbarHostState = remember { SnackbarHostState() }

                val showSnackbar: (UiText) -> Unit = { message ->
                    coroutineScope.launch {
                        snackbarHostState.currentSnackbarData?.dismiss()
                        snackbarHostState.showSnackbar(
                            message = message.asString(context),
                            duration = SnackbarDuration.Short
                        )
                    }
                }

                val mainViewModel = getViewModel<MainViewModel>()
                val state by mainViewModel.state.collectAsStateWithLifecycle()

                installSplashScreen().setKeepOnScreenCondition {
                    state.isLoading
                }

                if (!state.isLoading) {
                    val startDestination = when (state.shouldShowAuthScreen) {
                        true, null -> Route.Auth
                        false -> Route.Calendar
                    }

                    Scaffold(
                        snackbarHost = {
                            SnackbarHost(snackbarHostState) {
                                Snackbar(snackbarData = it)
                            }
                        },
                        contentWindowInsets = WindowInsets.safeDrawing
                    ) { _ ->
                        NavHost(
                            navController = navController,
                            startDestination = startDestination,
                        ) {
                            composable<Route.Auth> {
                                AuthScreenRoot(
                                    showSnackbar = { showSnackbar(it) },
                                    onLoginSuccess = { navController.navigate(Route.Calendar) }
                                )
                            }
                            composable<Route.Calendar> {
                                CalendarScreenRoot(
                                    showSnackbar = { showSnackbar(it) },
                                    onSettingsClick = { navController.navigate(Route.Settings) }
                                )
                            }
                            composable<Route.Settings> {
                                SettingsScreenRoot(
                                    showSnackbar = { showSnackbar(it) },
                                    onBackClick = { navController.navigateUp() },
                                    onLogoutClick = {
                                        navController.navigate(Route.Auth) {
                                            popUpTo(Route.Calendar)
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
