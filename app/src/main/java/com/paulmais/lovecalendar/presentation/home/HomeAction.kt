package com.paulmais.lovecalendar.presentation.home

interface HomeAction {
    data class OnItemClick(val title: String) : HomeAction
}