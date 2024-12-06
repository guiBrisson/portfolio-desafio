package me.brisson.g1.screen.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.brisson.g1.core.data.repository.FeedRepository

class FeedViewModel(
    private val feedRepository: FeedRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow<FeedUiState>(FeedUiState.Loading)
    val uiState: StateFlow<FeedUiState> = _uiState.asStateFlow()

    fun handleUiEvent(event: FeedUiEvent) {
        when (event) {
            FeedUiEvent.FetchFeed -> fetchFeed()
            FeedUiEvent.LoadNextPage -> loadNextPage()
        }
    }

    private fun fetchFeed() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val g1Feed = feedRepository.getFeed("g1")
                _uiState.value = FeedUiState.Success(g1Feed)
            } catch (e: Exception) {
                e.printStackTrace()
                _uiState.value = FeedUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    private fun loadNextPage() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                when (val uiState = _uiState.value) {
                    is FeedUiState.Success -> {
                        val feed = feedRepository.getFeedPage(uiState.feed.pagination)
                        val newFeedItems = uiState.feed.items + feed.items
                        _uiState.value = FeedUiState.Success(feed.copy(items = newFeedItems))
                    }

                    else -> Unit
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    companion object {
        val FEED_REPOSITORY_KEY = object : CreationExtras.Key<FeedRepository> {}
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val feedRepository = this[FEED_REPOSITORY_KEY] as FeedRepository
                FeedViewModel(feedRepository)
            }
        }
    }
}
