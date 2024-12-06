package me.brisson.g1.screen.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.MutableCreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import me.brisson.g1.core.data.repository.FeedRepository
import me.brisson.g1.ui.theme.G1Theme

@Composable
fun FeedRouter(
    modifier: Modifier = Modifier,
    feedRepository: FeedRepository,
) {
    val extras = MutableCreationExtras().apply {
        set(FeedViewModel.FEED_REPOSITORY_KEY, feedRepository)
    }
    val viewModel: FeedViewModel = viewModel<FeedViewModel>(
        factory = FeedViewModel.Factory,
        extras = extras,
    )
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.fetchFeed()
    }

    FeedScreen(
        modifier = modifier,
        uiState = uiState,
    )
}

@Composable
internal fun FeedScreen(
    modifier: Modifier = Modifier,
    uiState: FeedUiState,
) {
    Column(modifier = modifier) {
        when (uiState) {
            FeedUiState.Loading -> LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth(),
            )

            is FeedUiState.Error -> Text(
                text = uiState.message,
            )

            is FeedUiState.Success -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(vertical = 24.dp, horizontal = 16.dp),
                ) {
                    items(uiState.feed.items) { item ->
                        Column(modifier = Modifier.fillMaxWidth()) {
                            item.label?.let { label ->
                                Text(text = label)
                            }

                            item.title?.let { title ->
                                Text(
                                    modifier = Modifier.padding(bottom = 4.dp, top = 8.dp),
                                    text = title,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                )
                            }

                            item.summary?.let { sum ->
                                Text(modifier = Modifier.padding(bottom = 4.dp), text = sum)
                            }

                            item.imageUrl?.let { imageUrl ->
                                AsyncImage(
                                    modifier = Modifier.padding(top = 4.dp, bottom = 8.dp).fillMaxWidth(),
                                    model = imageUrl,
                                    contentDescription = null,
                                    contentScale = ContentScale.FillWidth,
                                )
                            }

                            item.metadata?.let { metadata ->
                                Text(
                                    modifier = Modifier.padding().fillMaxWidth(),
                                    text = metadata,
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                                    textAlign = TextAlign.Right,
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .padding(top = 40.dp)
                                    .fillMaxWidth()
                                    .height(1.2.dp)
                                    .background(MaterialTheme.colorScheme.onBackground),
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun PreviewFeedScreen() {
    val uiState = FeedUiState.Loading

    G1Theme {
        FeedScreen(
            modifier = Modifier.fillMaxSize(),
            uiState = uiState,
        )
    }
}
