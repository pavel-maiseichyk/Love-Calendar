package com.paulmais.lovecalendar.presentation.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.paulmais.lovecalendar.presentation.ui.theme.LoveCalendarTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreenRoot(
    viewModel: HomeViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    HomeScreen(state = state)
}

@Composable
private fun HomeScreen(
    state: HomeState
) {

}

@Preview
@Composable
private fun HomeScreenPreview() {
    LoveCalendarTheme {
        HomeScreen(
            state = HomeState()
        )
    }
}