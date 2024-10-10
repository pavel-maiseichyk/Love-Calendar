package com.paulmais.lovecalendar.presentation.home

import com.paulmais.lovecalendar.domain.model.DaysUntilItem

data class HomeState(
    val daysUntilList: List<DaysUntilItem> = emptyList()
)