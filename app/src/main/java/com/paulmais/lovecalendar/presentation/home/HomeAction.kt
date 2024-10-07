package com.paulmais.lovecalendar.presentation.home

import com.paulmais.lovecalendar.domain.model.AppDate

sealed class HomeAction {
    data object OnEditClick: HomeAction()
    data object OnConfirmEditClick: HomeAction()
    data object OnUndoEditClick: HomeAction()
    data class OnDateTap(val appDate: AppDate): HomeAction()
}