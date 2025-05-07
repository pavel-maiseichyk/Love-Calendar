package com.paulmais.lovecalendar.auth.domain

import com.paulmais.lovecalendar.core.domain.DataError
import com.paulmais.lovecalendar.core.domain.EmptyResult
import com.paulmais.lovecalendar.core.domain.Result

class FakeAuthRepository : AuthRepository {
    var error: DataError.Network? = null

    override suspend fun register(email: String, password: String): EmptyResult<DataError.Network> {
        return if (error != null) Result.Error(error!!) else Result.Success(Unit)
    }

    override suspend fun login(email: String, password: String): EmptyResult<DataError.Network> {
        return if (error != null) Result.Error(error!!) else Result.Success(Unit)
    }

    override suspend fun logout(): EmptyResult<DataError.Network> {
        return if (error != null) Result.Error(error!!) else Result.Success(Unit)
    }
}