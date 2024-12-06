package me.brisson.g1.core.model

data class Feed(
    val items: List<FeedItem>,
    val pagination: FeedPagination,
)

data class FeedItem(
    val id: String,
    val type: String,
    val imageUrl: String?,
    val metadata: String?,
    val label: String?,
    val section: String?,
    val title: String?,
    val summary: String?,
    val lastPublication: String?,
    val publication: String?,
    val url: String?,
    val aggregatedPosts: List<FeedItem>?,
)

data class FeedPagination(
    val oferta: String,
    val product: String,
    val nextPage: Int,
)
