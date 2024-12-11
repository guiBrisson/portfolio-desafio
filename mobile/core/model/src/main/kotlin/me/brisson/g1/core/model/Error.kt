package me.brisson.g1.core.model

// Base error interface
sealed interface Error

sealed interface NetworkError: Error {
    data class ClientRequestError(val code: Int, val message: String = "") : NetworkError
    data class ServerResponseError(val code: Int, val message: String = "") : NetworkError
    data class IOError(val message: String?) : NetworkError
    data class SerializationError(val message: String?) : NetworkError
}

sealed interface DataError: Error {
    data class Network(val error: NetworkError): DataError
    data object OfertaIsNull: DataError
    data object TenantIdIsNull: DataError
}
