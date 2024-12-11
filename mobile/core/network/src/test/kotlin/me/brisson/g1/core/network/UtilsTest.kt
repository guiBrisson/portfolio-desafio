package me.brisson.g1.core.network

import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.respondError
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import io.ktor.utils.io.ByteReadChannel
import kotlinx.coroutines.test.runTest
import kotlinx.io.IOException
import kotlinx.serialization.SerializationException
import me.brisson.g1.core.model.NetworkError
import me.brisson.g1.core.model.Result
import me.brisson.g1.core.model.onFailure
import me.brisson.g1.core.model.onSuccess
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.test.assertTrue

class UtilsTest {

    @Test
    fun `safeRequest should return Success when request is successful`() = runTest {
        val client = httpMockClient {
            respond(content = ByteReadChannel(""))
        }
        val httpResponse = client.safeRequest { get("") }

        assertTrue(httpResponse is Result.Success)
    }

    @Test
    fun `safeRequest should return Failure with ClientRequestError when status code between 400-499`() =
        runTest {
            val client = httpMockClient {
                respondError(status = HttpStatusCode.Unauthorized)
            }

            client.safeRequest { get("") }
                .onSuccess { assertTrue(false, message = "This should never be a success") }
                .onFailure { error -> assertTrue(error is NetworkError.ClientRequestError) }
        }

    @Test
    fun `safeRequest should return Failure with ServerRequestError when status code between 500-599`() =
        runTest {
            val client = httpMockClient {
                respondError(status = HttpStatusCode.InternalServerError)
            }

            client.safeRequest { get("") }
                .onSuccess { assertTrue(false, message = "This should never be a success") }
                .onFailure { error -> assertTrue(error is NetworkError.ServerResponseError) }
        }

    @Test
    fun `safeRequest should return Failure with IOError when IOErrorException is thrown`() = runTest {
        val client = httpMockClient { throw IOException() }
        client.safeRequest { get("") }
            .onSuccess { assertTrue(false, message = "This should never be a success") }
            .onFailure { error -> assertTrue(error is NetworkError.IOError) }
    }

    @Test
    fun `safeRequest should return Failure with SerializationError when SerializationException is thrown`() = runTest {
        val client = httpMockClient { throw SerializationException() }
        client.safeRequest { get("") }
            .onSuccess { assertTrue(false, message = "This should never be a success") }
            .onFailure { error -> assertTrue(error is NetworkError.SerializationError) }
    }
}