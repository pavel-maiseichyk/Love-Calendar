package com.paulmais.lovecalendar.core.data.remote

import com.paulmais.lovecalendar.auth.data.model.TokenResponse
import com.paulmais.lovecalendar.auth.domain.SessionStorage
import com.paulmais.lovecalendar.auth.domain.model.AuthInfo
import com.paulmais.lovecalendar.core.domain.HttpRoutes
import com.paulmais.lovecalendar.core.domain.Result
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.slf4j.LoggerFactory

class HttpClientFactory(
    private val sessionStorage: SessionStorage
) {
    fun build(): HttpClient {
        return HttpClient(Android) {
            install(HttpRequestRetry) {
                maxRetries = 0
                retryOnExceptionIf { _, _ -> false }
            }

            install(HttpTimeout) {
                requestTimeoutMillis = 2000
                connectTimeoutMillis = 1000
                socketTimeoutMillis = 1000
            }

            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        prettyPrint = true
                        isLenient = true
                    }
                )
            }

            install(Logging) {
                logger = object : Logger {
                    private val logger = LoggerFactory.getLogger("KtorClient")

                    override fun log(message: String) {
                        logger.info(message)
                    }
                }
                level = LogLevel.ALL
            }

            defaultRequest {
                contentType(ContentType.Application.Json)
            }

            install(Auth) {
                bearer {
                    loadTokens {
                        val info = sessionStorage.get()
                        BearerTokens(
                            accessToken = info?.accessToken ?: "",
                            refreshToken = info?.refreshToken ?: "",
                        )
                    }
                    refreshTokens {
                        val info = sessionStorage.get()
                        val response = client.post<RefreshTokenRequest, TokenResponse>(
                            route = HttpRoutes.Refresh.route,
                            body = RefreshTokenRequest(refreshToken = info?.refreshToken ?: "")
                        )

                        when (response) {
                            is Result.Success -> {
                                val newAuthInfo = AuthInfo(
                                    accessToken = response.data.accessToken,
                                    refreshToken = response.data.refreshToken,
                                )
                                sessionStorage.set(newAuthInfo)
                                BearerTokens(newAuthInfo.accessToken, newAuthInfo.refreshToken)
                            }

                            else -> BearerTokens(accessToken = "", refreshToken = "")
                        }
                    }
                }
            }
        }
    }
}