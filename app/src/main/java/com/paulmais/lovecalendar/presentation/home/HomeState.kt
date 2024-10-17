package com.paulmais.lovecalendar.presentation.home

import com.paulmais.lovecalendar.presentation.home.components.DaysUntilItemUI

data class HomeState(
    val isLoading: Boolean = true,
    val daysUntilUIList: List<DaysUntilItemUI> = emptyList()
)