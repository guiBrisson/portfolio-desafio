package me.brisson.g1.screen.feed

import me.brisson.g1.core.model.Feed

enum class FeedTab(val productOrUri: String) {
    G1_TAB("g1"),
    AGRO_TAB("https://g1.globo.com/economia/agronegocios");
}

sealed interface FeedUiState {
    data object Loading : FeedUiState
    data class Success(val feed: Feed) : FeedUiState
    data class Error(val message: String) : FeedUiState
}

sealed interface FeedUiEvent {
    data object Refresh : FeedUiEvent
    data object FetchFeed : FeedUiEvent
    data object LoadNextPage : FeedUiEvent
    data class SelectTab(val tab: FeedTab) : FeedUiEvent
}
