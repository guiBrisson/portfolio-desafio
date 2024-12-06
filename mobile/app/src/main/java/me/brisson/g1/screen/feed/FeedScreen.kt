package me.brisson.g1.screen.feed

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.MutableCreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import me.brisson.g1.core.data.repository.FeedRepository
import me.brisson.g1.ui.components.FeedItemComponent
import me.brisson.g1.ui.theme.G1Theme
import me.brisson.g1.ui.utils.isAtBottom

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

    LaunchedEffect(Unit) { viewModel.handleUiEvent(FeedUiEvent.FetchFeed) }

    FeedScreen(
        modifier = modifier,
        uiState = uiState,
        onLoadNextPage = { viewModel.handleUiEvent(FeedUiEvent.LoadNextPage) },
    )
}

@Composable
internal fun FeedScreen(
    modifier: Modifier = Modifier,
    uiState: FeedUiState,
    onLoadNextPage: () -> Unit,
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
                val listState = rememberLazyListState()
                val isReadyToScroll = listState.isAtBottom()

                LaunchedEffect(isReadyToScroll) {
                    if (isReadyToScroll) onLoadNextPage()
                }

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    state = listState,
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(vertical = 24.dp, horizontal = 16.dp),
                ) {
                    items(uiState.feed.items) { item ->
                        FeedItemComponent(
                            modifier = Modifier.fillMaxSize(),
                            feedItem = item,
                        )
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
            onLoadNextPage = { },
        )
    }
}
