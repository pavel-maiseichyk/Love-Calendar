package com.paulmais.lovecalendar.auth.data

import com.paulmais.lovecalendar.auth.data.model.AuthRequest
import com.paulmais.lovecalendar.auth.data.model.TokenResponse
import com.paulmais.lovecalendar.auth.domain.AuthRepository
import com.paulmais.lovecalendar.auth.domain.SessionStorage
import com.paulmais.lovecalendar.auth.domain.model.AuthInfo
import com.paulmais.lovecalendar.core.data.remote.RefreshTokenRequest
import com.paulmais.lovecalendar.core.data.remote.post
import com.paulmais.lovecalendar.core.domain.DataError
import com.paulmais.lovecalendar.core.domain.EmptyResult
import com.paulmais.lovecalendar.core.domain.HttpRoutes
import com.paulmais.lovecalendar.core.domain.Result
import com.paulmais.lovecalendar.core.domain.andThen
import com.paulmais.lovecalendar.core.domain.asEmptyDataResult
import io.ktor.client.HttpClient
import io.ktor.client.plugins.auth.authProvider
import io.ktor.client.plugins.auth.providers.BearerAuthProvider

class AuthRepositoryImpl(
    private val client: HttpClient,
    private val sessionStorage: SessionStorage
) : AuthRepository {

    private suspend fun signBase(
        isLogin: Boolean,
        email: String,
        password: String
    ): EmptyResult<DataError.Network> {
        val result = client.post<AuthRequest, TokenResponse>(
            route = if (isLogin) HttpRoutes.Login.route else HttpRoutes.Register.route,
            body = AuthRequest(email, password)
        )
        if (result is Result.Success) {
            sessionStorage.set(
                AuthInfo(
                    accessToken = result.data.accessToken,
                    refreshToken = result.data.refreshToken
                )
            )
        }
        return result.asEmptyDataResult()
    }

    override suspend fun register(
        email: String,
        password: String
    ): EmptyResult<DataError.Network> {
        return signBase(isLogin = false, email, password)
    }

    override suspend fun login(
        email: String,
        password: String
    ): EmptyResult<DataError.Network> {
        return signBase(isLogin = true, email, password)
    }

    override suspend fun logout(): EmptyResult<DataError.Network> {
        val info = sessionStorage.get()
        val result = client.post<RefreshTokenRequest, Unit>(
            route = HttpRoutes.SignOut.route,
            body = RefreshTokenRequest(refreshToken = info?.refreshToken ?: "")
        ).andThen {
            sessionStorage.set(null)
            client.authProvider<BearerAuthProvider>()?.clearToken()
            Result.Success(Unit)
        }
        return result.asEmptyDataResult()
    }
}