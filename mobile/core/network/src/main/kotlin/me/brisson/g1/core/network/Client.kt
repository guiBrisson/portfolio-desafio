package me.brisson.g1.core.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

val httpAndroidClient: HttpClient = HttpClient(Android) {
    defaultRequest {
        url("https://native-leon.globo.com/")
    }

    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
        })
    }
}
