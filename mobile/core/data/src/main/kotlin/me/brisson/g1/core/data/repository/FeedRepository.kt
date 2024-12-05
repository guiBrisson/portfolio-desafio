package me.brisson.g1.core.data.repository

import io.ktor.client.call.body
import me.brisson.g1.core.data.model.BaseFeedResponse
import me.brisson.g1.core.data.model.FeedResponse
import me.brisson.g1.core.data.model.asModel
import me.brisson.g1.core.data.model.filterMateriaAndBasicoType
import me.brisson.g1.core.model.Feed
import me.brisson.g1.core.model.FeedItem
import me.brisson.g1.core.model.FeedPagination
import me.brisson.g1.core.network.FeedApi
import me.brisson.g1.core.network.feedApi

// TODO: handle errors
interface FeedRepository {
    suspend fun getFeed(productOrUri: String): Feed
    suspend fun getFeedPage(feedPagination: FeedPagination): Feed
}

fun feedRepository(api: FeedApi = feedApi()) = object : FeedRepository {
    override suspend fun getFeed(productOrUri: String): Feed {
        val response: BaseFeedResponse = api.getFeed(productOrUri).body<BaseFeedResponse>()
        val feedItems: List<FeedItem> = response.feed.falkor.items
            .filterMateriaAndBasicoType()
            .map { it.asModel() }

        val pagination = FeedPagination(
            oferta = response.feed.oferta,
            product = productOrUri, //TODO: does it work if it is a uri?
            nextPage = response.feed.falkor.nextPage,
        )

        return Feed(
            items = feedItems,
            pagination = pagination,
        )
    }

    override suspend fun getFeedPage(feedPagination: FeedPagination): Feed {
        val response: FeedResponse = api.getFeedPage(feedPagination).body<FeedResponse>()
        val feedItems: List<FeedItem> = response.falkor.items.map { it.asModel() }

        return Feed(
            items = feedItems,
            pagination = feedPagination.copy(nextPage = response.falkor.nextPage),
        )
    }
}
