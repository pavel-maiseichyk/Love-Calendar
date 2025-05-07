package com.paulmais.lovecalendar.auth.data.mapper

import com.paulmais.lovecalendar.auth.data.model.AuthInfoSerializable
import com.paulmais.lovecalendar.auth.domain.model.AuthInfo

fun AuthInfo.toAuthInfoSerializable(): AuthInfoSerializable {
    return AuthInfoSerializable(
        accessToken = accessToken,
        refreshToken = refreshToken
    )
}

fun AuthInfoSerializable.toAuthInfo(): AuthInfo {
    return AuthInfo(
        accessToken = accessToken,
        refreshToken = refreshToken
    )
}