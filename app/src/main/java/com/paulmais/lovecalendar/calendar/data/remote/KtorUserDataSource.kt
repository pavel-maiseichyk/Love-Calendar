package com.paulmais.lovecalendar.calendar.data.remote

import com.paulmais.lovecalendar.auth.data.model.UserResponse
import com.paulmais.lovecalendar.calendar.domain.repository.RemoteUserDataSource
import com.paulmais.lovecalendar.core.data.remote.delete
import com.paulmais.lovecalendar.core.data.remote.get
import com.paulmais.lovecalendar.core.data.remote.put
import com.paulmais.lovecalendar.core.domain.DataError
import com.paulmais.lovecalendar.core.domain.EmptyResult
import com.paulmais.lovecalendar.core.domain.HttpRoutes
import com.paulmais.lovecalendar.core.domain.Result
import com.paulmais.lovecalendar.core.domain.asEmptyDataResult
import com.paulmais.lovecalendar.core.domain.map
import io.ktor.client.HttpClient

class KtorUserDataSource(
    private val client: HttpClient
) : RemoteUserDataSource {
    override suspend fun getUser(): Result<UserDTO, DataError.Network> {
        val result = client.get<UserResponse>(route = HttpRoutes.Users.route)
        return result.map { it.user }
    }

    override suspend fun updateUser(user: UserDTO): EmptyResult<DataError.Network> {
        val result = client.put<UserDTO, Unit>(
            route = HttpRoutes.Users.route,
            body = user
        )
        return result.asEmptyDataResult()
    }

    override suspend fun deleteUser(): EmptyResult<DataError.Network> {
        val result = client.delete<Unit>(route = HttpRoutes.Users.route)
        return result.asEmptyDataResult()
    }
}