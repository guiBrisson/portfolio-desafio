package me.brisson.g1.core.network

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import me.brisson.g1.core.model.FeedPagination
import me.brisson.g1.core.model.NetworkError
import me.brisson.g1.core.model.Result

interface FeedApi {
    suspend fun getFeed(productOrUri: String): Result<HttpResponse, NetworkError>
    suspend fun getFeedPage(feedPagination: FeedPagination): Result<HttpResponse, NetworkError>
}

fun feedApi(client: HttpClient = httpAndroidClient): FeedApi = object : FeedApi {
    private val ENDPOINT = "feed/"

    override suspend fun getFeed(productOrUri: String): Result<HttpResponse, NetworkError> =
        client.safeRequest { get(ENDPOINT + productOrUri) }

    override suspend fun getFeedPage(
        feedPagination: FeedPagination,
    ): Result<HttpResponse, NetworkError> = client.safeRequest {
        get(ENDPOINT + "page/" + feedPagination.tenantId + "/" + feedPagination.oferta + "/" + feedPagination.nextPage)
    }
}
