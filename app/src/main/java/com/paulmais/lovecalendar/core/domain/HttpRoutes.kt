package com.paulmais.lovecalendar.core.domain

sealed class HttpRoutes(val route: String) {
    companion object {
        const val BASE_URL = "http://10.0.2.2:8080"
    }

    data object Refresh : HttpRoutes("/refresh")
    data object Register : HttpRoutes("/sign_up")
    data object Login : HttpRoutes("/sign_in")
    data object Users : HttpRoutes("/users")
    data object SignOut : HttpRoutes("/sign_out")
}