package me.brisson.g1.ui.components

import androidx.compose.foundation.background
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import me.brisson.g1.core.model.FeedItem


@Composable
fun FeedItemComponent(
    modifier: Modifier = Modifier,
    feedItem: FeedItem,
) {
    Column(modifier = modifier then Modifier) {
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
                text = title,
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
                contentDescription = null,
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
) {
    aggregatedPosts.forEach { aggregatedItem ->
        aggregatedItem.title?.let { title ->
            Row(
                modifier = modifier then Modifier.padding(
                    start = 12.dp,
                    top = 16.dp,
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
