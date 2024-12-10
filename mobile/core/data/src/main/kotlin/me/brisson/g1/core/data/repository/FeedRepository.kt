package me.brisson.g1.core.data.repository

import io.ktor.client.call.body
import me.brisson.g1.core.data.model.BaseFeedResponse
import me.brisson.g1.core.data.model.asModel
import me.brisson.g1.core.data.model.filterMateriaAndBasicoType
import me.brisson.g1.core.model.DataError
import me.brisson.g1.core.model.Feed
import me.brisson.g1.core.model.FeedItem
import me.brisson.g1.core.model.FeedPagination
import me.brisson.g1.core.network.FeedApi
import me.brisson.g1.core.network.feedApi
import me.brisson.g1.core.model.Result
import me.brisson.g1.core.model.mapFailure
import me.brisson.g1.core.model.mapSuccess

interface FeedRepository {
    suspend fun getFeed(productOrUri: String): Result<Feed, DataError>
    suspend fun getFeedPage(feedPagination: FeedPagination): Result<Feed, DataError>
}

fun feedRepository(api: FeedApi = feedApi()) = object : FeedRepository {
    override suspend fun getFeed(productOrUri: String): Result<Feed, DataError> {
        return api.getFeed(productOrUri)
            .mapFailure { DataError.Network(it) }
            .mapSuccess { httpResponse ->
                val response: BaseFeedResponse = httpResponse.body<BaseFeedResponse>()
                val feedItems: List<FeedItem> = response.feed.falkor.items
                    .filterMateriaAndBasicoType()
                    .map { it.asModel() }

                val oferta = response.feed.oferta ?: return Result.Failure(DataError.OfertaIsNull)
                val tenantId =
                    response.resource?.tenantId ?: return Result.Failure(DataError.TenantIdIsNull)

                val pagination = FeedPagination(
                    oferta = oferta,
                    tenantId = tenantId,
                    nextPage = response.feed.falkor.nextPage,
                )

                return Result.Success(
                    Feed(
                        items = feedItems,
                        pagination = pagination,
                    )
                )
            }
    }

    override suspend fun getFeedPage(feedPagination: FeedPagination): Result<Feed, DataError> {
        return api.getFeedPage(feedPagination)
            .mapFailure { DataError.Network(it) }
            .mapSuccess { httpResponse ->
                val response: BaseFeedResponse = httpResponse.body<BaseFeedResponse>()
                val feedItems: List<FeedItem> = response.feed.falkor.items.map { it.asModel() }

                return Result.Success(
                    Feed(
                        items = feedItems,
                        pagination = feedPagination.copy(nextPage = response.feed.falkor.nextPage),
                    )
                )
            }
    }
}
