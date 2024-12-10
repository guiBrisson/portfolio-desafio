package me.brisson.g1.core.model

typealias RootError = Error

sealed interface Result<out D, out E: RootError> {
    data class Success<out D, out E: RootError>(val data: D): Result<D, E>
    data class Failure<out D, out E: RootError>(val error: E): Result<D, E>
}

fun <D, E : RootError, F : RootError> Result<D, E>.mapFailure(transform: (E) -> F): Result<D, F> {
    return when (this) {
        is Result.Success -> Result.Success(data)
        is Result.Failure -> Result.Failure(transform(error))
    }
}

inline fun <D, E : RootError, R> Result<D, E>.mapSuccess(transform: (D) -> R): Result<R, E> {
    return when (this) {
        is Result.Success -> Result.Success(transform(data))
        is Result.Failure -> Result.Failure(error)
    }
}

inline fun <D, E : RootError, R> Result<D, E>.onSuccess(block: (D) -> R): Result<D, E> {
    if (this is Result.Success) {
        block(data)
    }
    return this
}

inline fun <D, E : RootError, R> Result<D, E>.onFailure(block: (E) -> R): Result<D, E> {
    if (this is Result.Failure) {
        block(error)
    }
    return this
}
