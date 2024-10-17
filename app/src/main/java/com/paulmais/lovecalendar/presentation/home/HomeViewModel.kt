package com.paulmais.lovecalendar.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paulmais.lovecalendar.domain.use_case.GenerateDaysLeft
import com.paulmais.lovecalendar.presentation.home.components.toDaysUntilItemUI
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
            generateDaysLeft.execute().collect { daysUntilList ->
                _state.update { it.copy(isLoading = true) }
                val daysUntilUIList = daysUntilList.map { it.toDaysUntilItemUI() }
                _state.update { it.copy(daysUntilUIList = daysUntilUIList, isLoading = false) }
            }
        }
    }

    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.OnItemClick -> {
                _state.update {
                    it.copy(
                        daysUntilUIList = state.value.daysUntilUIList.map { item ->
                            if (item.title == action.title) {
                                item.copy(isShowingDate = !item.isShowingDate)
                            } else item
                        }
                    )
                }
            }
        }
    }
}