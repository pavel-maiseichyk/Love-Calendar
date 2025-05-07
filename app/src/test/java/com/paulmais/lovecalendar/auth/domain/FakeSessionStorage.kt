package com.paulmais.lovecalendar.auth.domain

import com.paulmais.lovecalendar.auth.domain.model.AuthInfo

class FakeSessionStorage : SessionStorage {
    var authInfo: AuthInfo? = null

    override suspend fun get(): AuthInfo? {
        return authInfo
    }

    override suspend fun set(info: AuthInfo?) {
        authInfo = info
    }
}