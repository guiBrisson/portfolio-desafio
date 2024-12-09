package me.brisson.g1.screen.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import me.brisson.g1.core.data.repository.FeedRepository
import me.brisson.g1.screen.feed.FeedUiEvent.*

class FeedViewModel(
    private val feedRepository: FeedRepository,
) : ViewModel() {
    private val _tabSelected: MutableStateFlow<FeedTab> = MutableStateFlow(FeedTab.G1_TAB)
    val tabSelected: StateFlow<FeedTab> = _tabSelected.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    private val _uiState = MutableStateFlow<FeedUiState>(FeedUiState.Loading)
    val uiState: StateFlow<FeedUiState> = _uiState
        .onStart { loadFeed(_tabSelected.value.productOrUri) } // Fetches the initial feed data
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), FeedUiState.Loading)

    fun handleUiEvent(event: FeedUiEvent) {
        when (event) {
            Refresh -> refresh()
            FetchFeed -> loadFeed(_tabSelected.value.productOrUri)
            LoadNextPage -> loadFeedNextPage()
            is SelectTab -> selectTag(event.tab)
        }
    }

    private fun refresh() {
        _isRefreshing.value = true
        loadFeed(_tabSelected.value.productOrUri)
        viewModelScope.launch {
            delay(200) // delay is needed for the animation to finish gracefully
            _isRefreshing.value = false
        }
    }

    private fun loadFeed(productOrUri: String) {
        _uiState.value = FeedUiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val g1Feed = feedRepository.getFeed(productOrUri)
                _uiState.value = FeedUiState.Success(g1Feed)
            } catch (e: Exception) {
                e.printStackTrace()
                _uiState.value = FeedUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    private fun loadFeedNextPage() {
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

    private fun selectTag(tab: FeedTab) {
        // Nothing should happen if the user select the already selected tab
        if (tab == _tabSelected.value) return

        _tabSelected.value = tab
        loadFeed(_tabSelected.value.productOrUri)
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
