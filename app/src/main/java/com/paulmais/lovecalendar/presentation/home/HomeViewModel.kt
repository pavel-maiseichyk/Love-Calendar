package com.paulmais.lovecalendar.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paulmais.lovecalendar.domain.use_case.GenerateDaysLeft
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    generateDaysLeft: GenerateDaysLeft
) : ViewModel() {

    private var _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val daysUntilList = generateDaysLeft.execute()
            _state.update { it.copy(daysUntilList = daysUntilList) }
        }
    }
}