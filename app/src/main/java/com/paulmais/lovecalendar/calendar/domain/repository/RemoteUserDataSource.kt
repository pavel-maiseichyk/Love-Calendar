package com.paulmais.lovecalendar.calendar.domain.repository

import com.paulmais.lovecalendar.calendar.data.remote.UserDTO
import com.paulmais.lovecalendar.core.domain.DataError
import com.paulmais.lovecalendar.core.domain.EmptyResult
import com.paulmais.lovecalendar.core.domain.Result

interface RemoteUserDataSource {
    suspend fun getUser(): Result<UserDTO, DataError.Network>
    suspend fun updateUser(user: UserDTO): EmptyResult<DataError.Network>
    suspend fun deleteUser(): EmptyResult<DataError.Network>
}