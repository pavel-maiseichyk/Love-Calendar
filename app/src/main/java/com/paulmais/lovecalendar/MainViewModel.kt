package com.paulmais.lovecalendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paulmais.lovecalendar.auth.domain.SessionStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class MainViewModel(
    private val sessionStorage: SessionStorage
) : ViewModel() {

    private var _state = MutableStateFlow(MainViewModelState())
    val state = _state.onStart {
        val authInfo = sessionStorage.get()
        val tokensDoNotExist = authInfo == null || authInfo.isEmpty()

        _state.update {
            it.copy(
                isLoading = false,
                shouldShowAuthScreen = tokensDoNotExist
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = MainViewModelState()
    )
}