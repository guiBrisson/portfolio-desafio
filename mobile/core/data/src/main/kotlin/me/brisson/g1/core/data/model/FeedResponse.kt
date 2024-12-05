package me.brisson.g1.core.data.model

import kotlinx.serialization.Serializable
import me.brisson.g1.core.model.FeedItem

@Serializable
data class BaseFeedResponse(
    val feed: FeedResponse,
)

@Serializable
data class FeedResponse(
    val oferta: String,
    val falkor: FalkorResponse
)

@Serializable
data class FalkorResponse(
    val items: List<FeedItemResponse>,
    val nextPage: Int,
)

@Serializable
data class FeedItemResponse(
    val id: String,
    val feedId: String? = null,
    val type: String,
    val aggregatedPosts: List<AggregatedPostItemResponse>? = null,
    val lastPublication: String? = null,
    val publication: String? = null,
    val content: ItemContentResponse? = null,
    val metadata: String? = null,
)

@Serializable
data class AggregatedPostItemResponse(
    val type: String,
    val content: ItemContentResponse,
)

@Serializable
data class ItemContentResponse(
    val chapeu: ItemContentChapeuResponse? = null,
    val identifier: String? = null,
    val image: ItemContentImageResponse? = null,
    val section: String? = null,
    val summary: String? = null,
    val title: String? = null,
    val type: String? = null,
    val url: String? = null,
) {
    @Serializable
    data class ItemContentImageResponse(
        val url: String,
    )

    @Serializable
    data class ItemContentChapeuResponse(
        val label: String,
    )
}

fun List<FeedItemResponse>.filterMateriaAndBasicoType(): List<FeedItemResponse> =
    this.filter { it.type == "materia" || it.type == "basico" }

fun FeedItemResponse.asModel(): FeedItem = FeedItem(
    id = id,
    type = type,
    imageUrl = content?.image?.url,
    metadata = metadata,
    label = content?.chapeu?.label,
    title = content?.title,
    summary = content?.summary,
    lastPublication = lastPublication,
    publication = publication,
    url = content?.url,
)
