package me.brisson.g1.core.network

import io.ktor.client.HttpClient
import io.ktor.client.statement.HttpResponse
import kotlinx.io.IOException
import kotlinx.serialization.SerializationException
import me.brisson.g1.core.model.NetworkError
import me.brisson.g1.core.model.Result

/**
 *  Makes a safe HTTP request using the provided [HttpClient] and request configuration [block].
 *
 *  This function wraps the request in a `try-catch` block to handle potential exceptions and
 *  returns a `Result` object.
 *
 *  @return A `Result` object containing either the successful [HttpResponse] or a [NetworkError]
 *  in case of failure.
 *
 *  [NetworkError.ClientRequestError] If the client request fails with a 4xx status code.
 *
 *  [NetworkError.ServerResponseError] If the server response fails with a 5xx status code.
 *
 *  [NetworkError.IOError] If an I/O error occurs during the request.
 *
 *  [NetworkError.SerializationError] If a serialization error occurs during the request.
 */
inline fun HttpClient.safeRequest(
    block: HttpClient.() -> HttpResponse,
): Result<HttpResponse, NetworkError> {
    return try {
        val response = block()

        return when (val code = response.status.value) {
            in 400..499 -> Result.Failure(NetworkError.ClientRequestError(code))
            in 500..599 -> Result.Failure(NetworkError.ServerResponseError(code))
            else -> Result.Success(response)
        }

    } catch (e: IOException) {
        Result.Failure(NetworkError.IOError(e.message))
    } catch (e: SerializationException) {
        Result.Failure(NetworkError.SerializationError(e.message))
    }
}
