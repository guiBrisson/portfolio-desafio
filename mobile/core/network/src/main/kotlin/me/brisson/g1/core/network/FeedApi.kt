package me.brisson.g1.core.network

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import me.brisson.g1.core.model.FeedPagination

interface FeedApi {
    suspend fun getFeed(productOrUri: String): HttpResponse
    suspend fun getFeedPage(feedPagination: FeedPagination): HttpResponse
}

fun feedApi(client: HttpClient = httpAndroidClient): FeedApi = object : FeedApi {
    private val ENDPOINT = "feed/"

    override suspend fun getFeed(productOrUri: String) = client.get(ENDPOINT + productOrUri)

    override suspend fun getFeedPage(feedPagination: FeedPagination): HttpResponse =
        client.get(ENDPOINT + "page/" + feedPagination.product + "/" + feedPagination.oferta + "/" + feedPagination.nextPage)
}
