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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.MutableCreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import me.brisson.g1.core.data.repository.FeedRepository
import me.brisson.g1.ui.components.FeedItemComponent
import me.brisson.g1.ui.components.G1AppBar
import me.brisson.g1.ui.components.ShimmerBox
import me.brisson.g1.ui.theme.G1Theme
import me.brisson.g1.ui.utils.isAtBottom

@Composable
fun FeedRouter(
    modifier: Modifier = Modifier,
    feedRepository: FeedRepository,
    onFeedItem: (url: String) -> Unit,
) {
    val viewModel: FeedViewModel = viewModel<FeedViewModel>(
        factory = FeedViewModel.Factory,
        extras = MutableCreationExtras().apply {
            set(FeedViewModel.FEED_REPOSITORY_KEY, feedRepository)
        }
    )
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val isRefreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) { viewModel.handleUiEvent(FeedUiEvent.FetchFeed) }

    Column(modifier = Modifier.fillMaxSize()) {
        G1AppBar()
        FeedScreen(
            modifier = modifier,
            uiState = uiState,
            isRefreshing = isRefreshing,
            onLoadNextPage = { viewModel.handleUiEvent(FeedUiEvent.LoadNextPage) },
            onRefresh = { viewModel.handleUiEvent(FeedUiEvent.Refresh) },
            onFeedItem = onFeedItem,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun FeedScreen(
    modifier: Modifier = Modifier,
    uiState: FeedUiState,
    isRefreshing: Boolean,
    onLoadNextPage: () -> Unit,
    onRefresh: () -> Unit,
    onFeedItem: (url: String) -> Unit,
) {
    val pullToRefreshState = rememberPullToRefreshState()

    PullToRefreshBox(
        state = pullToRefreshState,
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        indicator = {
            Indicator(
                modifier = Modifier.align(Alignment.TopCenter),
                isRefreshing = isRefreshing,
                state = pullToRefreshState,
                color = MaterialTheme.colorScheme.primaryContainer,
                containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
            )
        }
    ) {
        Column(modifier = modifier then Modifier.padding()) {
            if (isRefreshing) {
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    progress = { pullToRefreshState.distanceFraction },
                )
                return@Column
            }

            when (uiState) {
                FeedUiState.Loading -> {
                    repeat(3) { LoadingFeedItem() }
                }

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
                                onFeedItem = { clickedItem ->
                                    clickedItem.url?.let { onFeedItem(it) }
                                },
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LoadingFeedItem() {
    // Represents label
    ShimmerBox(
        modifier = Modifier
            .padding(top = 12.dp, bottom = 8.dp)
            .padding(horizontal = 16.dp)
            .size(width = 120.dp, height = 20.dp),
    )

    // Represents title
    ShimmerBox(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(bottom = 4.dp)
            .padding(horizontal = 16.dp),
    )

    // Represents image
    ShimmerBox(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
            .padding(top = 4.dp)
            .padding(horizontal = 16.dp),
    )

    // Represents metadata
    ShimmerBox(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .padding(horizontal = 16.dp)
            .size(width = 200.dp, height = 20.dp),
    )

    // Represents Divider
    Box(
        modifier = Modifier
            .padding(vertical = 24.dp)
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .height(0.5.dp)
            .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)),
    )
}


@Preview(showSystemUi = true)
@Composable
private fun PreviewFeedScreen() {
    val uiState = FeedUiState.Loading

    G1Theme {
        FeedScreen(
            modifier = Modifier.fillMaxSize(),
            uiState = uiState,
            isRefreshing = false,
            onLoadNextPage = { },
            onRefresh = { },
            onFeedItem = { },
        )
    }
}
