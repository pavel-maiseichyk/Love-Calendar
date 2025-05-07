package com.paulmais.lovecalendar.core.domain

sealed interface DataError : Error {
    enum class Network : DataError {
        NO_INTERNET,
        SERIALIZATION,

        USER_ALREADY_EXISTS,
        INVALID_EMAIL_FORMAT,
        INVALID_PASSWORD_LENGTH,
        USER_NOT_FOUND,
        INVALID_PASSWORD,
        INVALID_REFRESH_TOKEN,
        INVALID_ACCESS_TOKEN,
        INTERNAL_SERVER_ERROR,
        UNKNOWN
    }

    enum class Local : DataError {
        DISK_FULL,
        MISSING_DATA,
        UNKNOWN
    }
}