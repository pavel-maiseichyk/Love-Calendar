package com.paulmais.lovecalendar.core.domain

sealed interface Result<out D, out E : Error> {
    data class Success<out D>(val data: D) : Result<D, Nothing>
    data class Error<out E : com.paulmais.lovecalendar.core.domain.Error>(val error: E) :
        Result<Nothing, E>
}

inline fun <T, E : Error, R> Result<T, E>.map(map: (T) -> R): Result<R, E> {
    return when (this) {
        is Result.Error -> Result.Error(error)
        is Result.Success -> Result.Success(map(data))
    }
}

fun <T, E : Error> Result<T, E>.asEmptyDataResult(): EmptyResult<E> {
    return map { }
}

/**
 * Chain operations without caring about the previous result's data.
 * Useful for sequential operations where only success/failure matters.
 */
inline fun <T, E : Error, R> Result<T, E>.andThen(transform: () -> Result<R, E>): Result<R, E> {
    return when (this) {
        is Result.Success -> transform()
        is Result.Error -> Result.Error(error)
    }
}

/**
 * Maps a successful result to another result based on the transformation function.
 * This is useful for chaining operations that return Results.
 */
inline fun <T, E : Error, R> Result<T, E>.flatMap(transform: (T) -> Result<R, E>): Result<R, E> {
    return when (this) {
        is Result.Success -> transform(data)
        is Result.Error -> Result.Error(error)
    }
}

typealias EmptyResult<E> = Result<Unit, E>