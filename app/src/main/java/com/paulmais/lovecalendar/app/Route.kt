package com.paulmais.lovecalendar.app

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object Calendar: Route

    @Serializable
    data object Settings: Route
}