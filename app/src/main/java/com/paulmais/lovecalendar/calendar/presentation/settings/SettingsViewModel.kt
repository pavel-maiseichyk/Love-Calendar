package com.paulmais.lovecalendar.calendar.presentation.settings

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paulmais.lovecalendar.R
import com.paulmais.lovecalendar.calendar.domain.repository.UserRepository
import com.paulmais.lovecalendar.calendar.domain.util.DateUtil
import com.paulmais.lovecalendar.calendar.domain.util.DateUtil.formatToISO
import com.paulmais.lovecalendar.calendar.domain.util.DateUtil.reverseDateFormat
import com.paulmais.lovecalendar.core.domain.DataError
import com.paulmais.lovecalendar.core.domain.Result
import com.paulmais.lovecalendar.core.presentation.UiText
import com.paulmais.lovecalendar.core.presentation.asUiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.daysUntil

class SettingsViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SettingsState())
    val state = _state.asStateFlow()

    private val specialDateString = MutableStateFlow<String?>(null)

    init {
        viewModelScope.launch {
            userRepository.getStartingDate().collect { date ->
                date?.let {
                    val formattedDate = date.reverseDateFormat()
                    specialDateString.value = formattedDate
                    _state.update { it.copy(specialDateString = formattedDate) }
                }
            }
        }
    }

    private val _uiChannel = Channel<SettingsEvent>()
    val uiChannel = _uiChannel.receiveAsFlow()

    fun onAction(action: SettingsAction) {
        when (action) {
            SettingsAction.DateClicked -> {
                _state.update {
                    it.copy(isEditingSpecialDate = true)
                }
            }

            SettingsAction.CancelUpdateDateClicked -> {
                _state.update {
                    it.copy(
                        isEditingSpecialDate = false,
                        specialDateString = specialDateString.value ?: state.value.specialDateString
                    )
                }
            }

            SettingsAction.UpdateDateClicked -> viewModelScope.launch {
                try {
                    val date = state.value.specialDateString.formatToISO()

                    if (date.daysUntil(DateUtil.now()) < 0) {
                        _uiChannel.send(SettingsEvent.ShowMessage(UiText.StringResource(R.string.error_future_date)))
                        return@launch
                    }

                    userRepository.updateUserProfile(specialDate = date)
                    _state.update { it.copy(isEditingSpecialDate = false) }
                    _uiChannel.send(SettingsEvent.ShowMessage(UiText.StringResource(R.string.success)))
                } catch (e: Exception) {
                    _uiChannel.send(SettingsEvent.ShowMessage(UiText.StringResource(R.string.error_bad_date_format)))
                }
            }

            SettingsAction.LogoutClicked -> viewModelScope.launch {
                when (val result = userRepository.logout()) {
                    is Result.Error<DataError> -> {
                        _uiChannel.send(SettingsEvent.ShowMessage(result.error.asUiText()))
                    }

                    is Result.Success<Unit> -> {
                        _uiChannel.send(SettingsEvent.Logout)
                    }
                }
            }

            SettingsAction.BackClicked -> viewModelScope.launch {
                _uiChannel.send(SettingsEvent.NavigateBack)
            }

            is SettingsAction.DateChanged -> {
                if (action.text.length <= 8 && action.text.isDigitsOnly()) {
                    _state.update { it.copy(specialDateString = action.text) }
                }
            }
        }
    }
}