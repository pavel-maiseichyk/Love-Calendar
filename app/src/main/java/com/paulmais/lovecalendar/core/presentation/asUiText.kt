package com.paulmais.lovecalendar.core.presentation

import com.paulmais.lovecalendar.R
import com.paulmais.lovecalendar.core.domain.DataError

fun DataError.asUiText(): UiText {
    return when (this) {
        DataError.Local.DISK_FULL -> UiText.StringResource(
            R.string.error_disk_full
        )

        DataError.Network.NO_INTERNET -> UiText.StringResource(
            R.string.error_no_internet
        )

        DataError.Network.SERIALIZATION -> UiText.StringResource(
            R.string.error_serialization
        )

        DataError.Network.UNKNOWN -> UiText.StringResource(R.string.error_unknown)

        DataError.Network.USER_ALREADY_EXISTS -> UiText.StringResource(
            R.string.error_user_exists
        )

        DataError.Network.INVALID_EMAIL_FORMAT -> UiText.StringResource(
            R.string.error_invalid_email
        )

        DataError.Network.INVALID_PASSWORD_LENGTH -> UiText.StringResource(
            R.string.error_invalid_password_length
        )

        DataError.Network.USER_NOT_FOUND -> UiText.StringResource(
            R.string.error_user_not_found
        )

        DataError.Network.INVALID_PASSWORD -> UiText.StringResource(
            R.string.error_invalid_password
        )

        DataError.Network.INVALID_REFRESH_TOKEN -> UiText.StringResource(
            R.string.error_invalid_refresh_token
        )

        DataError.Network.INVALID_ACCESS_TOKEN -> UiText.StringResource(
            R.string.error_invalid_access_token
        )

        DataError.Network.INTERNAL_SERVER_ERROR -> UiText.StringResource(
            R.string.error_server_error
        )

        DataError.Local.MISSING_DATA -> UiText.StringResource(
            R.string.error_missing_data
        )
        DataError.Local.UNKNOWN -> UiText.StringResource(
            R.string.error_unknown
        )
    }
}