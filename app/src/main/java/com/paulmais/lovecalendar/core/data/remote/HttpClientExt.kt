package com.paulmais.lovecalendar.core.data.remote

import com.paulmais.lovecalendar.auth.data.model.ErrorResponse
import com.paulmais.lovecalendar.core.domain.DataError
import com.paulmais.lovecalendar.core.domain.HttpRoutes.Companion.BASE_URL
import com.paulmais.lovecalendar.core.domain.Result
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.CancellationException
import kotlinx.serialization.SerializationException

suspend inline fun <reified Response : Any> HttpClient.get(
    route: String,
    queryParameters: Map<String, Any?> = mapOf()
): Result<Response, DataError.Network> {
    return safeCall {
        get {
            url(constructRoute(route))
            queryParameters.forEach { (key, value) ->
                parameter(key, value)
            }
        }
    }
}

suspend inline fun <reified Request, reified Response : Any> HttpClient.post(
    route: String,
    body: Request,
): Result<Response, DataError.Network> {
    return safeCall {
        post {
            url(constructRoute(route))
            setBody(body)
        }
    }
}

suspend inline fun <reified Request, reified Response : Any> HttpClient.put(
    route: String,
    body: Request,
): Result<Response, DataError.Network> {
    return safeCall {
        put {
            url(constructRoute(route))
            setBody(body)
        }
    }
}

suspend inline fun <reified Response : Any> HttpClient.delete(
    route: String,
    queryParameters: Map<String, Any?> = mapOf()
): Result<Response, DataError.Network> {
    return safeCall {
        delete {
            url(constructRoute(route))
            queryParameters.forEach { (key, value) ->
                parameter(key, value)
            }
        }
    }
}

suspend inline fun <reified T> safeCall(execute: () -> HttpResponse): Result<T, DataError.Network> {
    val response = try {
        execute()
    } catch (e: UnresolvedAddressException) {
        e.printStackTrace()
        return Result.Error(DataError.Network.NO_INTERNET)
    } catch (e: SerializationException) {
        e.printStackTrace()
        return Result.Error(DataError.Network.SERIALIZATION)
    } catch (e: Exception) {
        if (e is CancellationException) throw e
        e.printStackTrace()
        return Result.Error(DataError.Network.UNKNOWN)
    }

    return responseToResult(response)
}

suspend inline fun <reified T> responseToResult(response: HttpResponse): Result<T, DataError.Network> {
    return when (response.status.value) {
        in 200..299 -> Result.Success(response.body<T>())
        else -> {
            val errorResponse = response.body<ErrorResponse>()
            val error = when (errorResponse.errorCode) {
                "USER_ALREADY_EXISTS" -> DataError.Network.USER_ALREADY_EXISTS
                "INVALID_EMAIL_FORMAT" -> DataError.Network.INVALID_EMAIL_FORMAT
                "INVALID_PASSWORD_LENGTH" -> DataError.Network.INVALID_PASSWORD_LENGTH
                "USER_NOT_FOUND" -> DataError.Network.USER_NOT_FOUND
                "INVALID_PASSWORD" -> DataError.Network.INVALID_PASSWORD
                "INVALID_REFRESH_TOKEN" -> DataError.Network.INVALID_REFRESH_TOKEN
                "INVALID_ACCESS_TOKEN" -> DataError.Network.INVALID_ACCESS_TOKEN
                "INTERNAL_SERVER_ERROR" -> DataError.Network.INTERNAL_SERVER_ERROR
                else -> DataError.Network.UNKNOWN
            }
            Result.Error(error)
        }
    }
}

fun constructRoute(route: String): String {
    return when {
        route.contains(BASE_URL) -> route
        route.startsWith("/") -> BASE_URL + route
        else -> "$BASE_URL/$route"
    }
}