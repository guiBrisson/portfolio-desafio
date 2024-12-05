package me.brisson.g1.screen.feed

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.MutableCreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
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
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(uiState.feed.items) { item ->
                        Text(text = item.title ?: "Unknown")
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
