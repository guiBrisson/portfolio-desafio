package me.brisson.g1.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import me.brisson.g1.R
import me.brisson.g1.core.model.FeedItem

@Composable
fun FeedItemComponent(
    modifier: Modifier = Modifier,
    feedItem: FeedItem,
    onFeedItem: (FeedItem) -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = modifier then Modifier.clickable(
            interactionSource = interactionSource,
            indication = null,
            onClick = { onFeedItem(feedItem) },
        ),
    ) {
        feedItem.label?.let { label ->
            Text(
                modifier = Modifier.padding(bottom = 8.dp),
                text = label,
                style = MaterialTheme.typography.labelLarge,
            )
        }

        feedItem.title?.let { title ->
            Text(
                modifier = Modifier.padding(bottom = 4.dp),
                text = title.trimIndent(),
                style = MaterialTheme.typography.headlineMedium,
            )
        }

        feedItem.summary?.let { sum ->
            Text(
                modifier = Modifier.padding(bottom = 4.dp),
                text = sum,
                style = MaterialTheme.typography.bodyLarge,
            )
        }

        feedItem.imageUrl?.let { imageUrl ->
            AsyncImage(
                modifier = Modifier
                    .padding(top = 4.dp)
                    .fillMaxWidth(),
                model = imageUrl,
                contentDescription = stringResource(R.string.feed_item_image),
                contentScale = ContentScale.FillWidth,
            )
        }

        feedItem.metadata?.let { metadata ->
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = metadata,
                style = MaterialTheme.typography.labelMedium,
            )
        }

        feedItem.aggregatedPosts?.let { aggregatedPosts ->
            FeedItemAggregatedPostsComponent(
                modifier = Modifier.fillMaxWidth(),
                aggregatedPosts = aggregatedPosts,
                onFeedItem = onFeedItem,
            )
        }

        Box(
            modifier = Modifier
                .padding(top = 24.dp)
                .fillMaxWidth()
                .height(0.5.dp)
                .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)),
        )
    }
}

@Composable
private fun FeedItemAggregatedPostsComponent(
    modifier: Modifier = Modifier,
    aggregatedPosts: List<FeedItem>,
    onFeedItem: (FeedItem) -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }

    aggregatedPosts.forEach { aggregatedItem ->
        aggregatedItem.title?.let { title ->
            Row(
                modifier = modifier then Modifier
                    .padding(
                        start = 12.dp,
                        top = 16.dp,
                    )
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        onClick = { onFeedItem(aggregatedItem) },
                    ),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Box(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .size(6.dp)
                        .background(MaterialTheme.colorScheme.primary)
                )

                Text(
                    text = title,
                    color = MaterialTheme.colorScheme.onBackground.copy(
                        alpha = 0.9f,
                    ),
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        }
    }
}

//TODO: Preview both components
