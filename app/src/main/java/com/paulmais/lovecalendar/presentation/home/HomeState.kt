package com.paulmais.lovecalendar.presentation.home

import androidx.compose.runtime.Immutable
import com.paulmais.lovecalendar.presentation.home.components.DaysUntilItemUI

@Immutable
data class HomeState(
    val isLoading: Boolean = true,
    val daysUntilUIList: List<DaysUntilItemUI> = emptyList()
)