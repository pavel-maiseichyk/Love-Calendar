package com.paulmais.lovecalendar.presentation.settings

import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paulmais.lovecalendar.domain.repository.MeetingsDataSource
import com.paulmais.lovecalendar.domain.util.DateUtil
import com.paulmais.lovecalendar.domain.util.DateUtil.formatToISO
import com.paulmais.lovecalendar.domain.util.DateUtil.reverseDateFormat
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.daysUntil

class SettingsViewModel(
    private val meetingsDataSource: MeetingsDataSource
) : ViewModel() {

    private val _state = MutableStateFlow(SettingsState())
    val state = _state.asStateFlow()

    private val eventChannel = Channel<SettingsEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            meetingsDataSource.getStartingDate().collect { date ->
                if (date != null) {
                    val formattedDate = date.reverseDateFormat()
                    _state.update { it.copy(specialDateString = formattedDate) }
                }
            }
        }
    }

    fun onAction(action: SettingsAction) {
        when (action) {
            is SettingsAction.OnDateClick -> {
                _state.update {
                    val specialDateString = state.value.specialDateString.filter { it.isDigit() }
                    it.copy(
                        isEditingSpecialDate = true,
                        specialDateTextFieldState = TextFieldState(specialDateString)
                    )
                }
            }

            is SettingsAction.OnCancelUpdateDateClick -> {
                _state.update { it.copy(isEditingSpecialDate = false) }
            }

            is SettingsAction.OnUpdateDateClick -> {
                viewModelScope.launch {
                    try {
                        val date =
                            state.value.specialDateTextFieldState.text.toString().formatToISO()

                        if (date.daysUntil(DateUtil.now()) < 0) {
                            eventChannel.send(SettingsEvent.ShowToast(message = "The date cannot be later than today."))
                            return@launch
                        }
                        meetingsDataSource.updateStartingDate(date)
                        _state.update { it.copy(isEditingSpecialDate = false) }
                        eventChannel.send(SettingsEvent.ShowToast(message = "Saved successfully!"))
                    } catch (e: Exception) {
                        println(e)
                        eventChannel.send(SettingsEvent.ShowToast(message = "Failure. Please, check your date again."))
                    }
                }
            }
        }
    }
}