package com.paulmais.lovecalendar.auth.domain

import com.paulmais.lovecalendar.auth.domain.model.AuthInfo

interface SessionStorage {
    suspend fun get(): AuthInfo?
    suspend fun set(info: AuthInfo?)
}