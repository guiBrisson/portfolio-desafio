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
import me.brisson.g1.core.model.DataError
import me.brisson.g1.core.model.onFailure
import me.brisson.g1.core.model.onSuccess
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
        .onStart { loadFeed(_tabSelected.value.productOrUri) } // Loads the initial feed data
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), FeedUiState.Loading)

    fun handleUiEvent(event: FeedUiEvent) {
        when (event) {
            Refresh -> refresh()
            FetchFeed -> loadFeed(_tabSelected.value.productOrUri)
            LoadNextPage -> loadFeedNextPage()
            is SelectTab -> selectTab(event.tab)
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
            feedRepository.getFeed(productOrUri)
                .onSuccess { feed -> _uiState.value = FeedUiState.Success(feed) }
                .onFailure { dataError ->
                    _uiState.value = FeedUiState.Error(mapDataErrorToString(dataError))
                }
        }
    }

    private fun loadFeedNextPage() {
        viewModelScope.launch(Dispatchers.IO) {

            // Should only load the next feed page if is a successful current state
            when (val state = _uiState.value) {
                is FeedUiState.Success -> {
                    feedRepository.getFeedPage(state.feed.pagination)
                        .onSuccess { feed ->
                            val newFeedItems = state.feed.items + feed.items
                            _uiState.value = FeedUiState.Success(feed.copy(items = newFeedItems))
                        }
                        .onFailure { dataError ->
                            _uiState.value = FeedUiState.Error(mapDataErrorToString(dataError))
                        }
                }

                else -> Unit
            }
        }
    }

    private fun selectTab(tab: FeedTab) {
        // Nothing should happen if the user select the already selected tab
        if (tab == _tabSelected.value) return

        _tabSelected.value = tab
        loadFeed(_tabSelected.value.productOrUri)
    }

    private fun mapDataErrorToString(error: DataError): String = when (error) {
        is DataError.Network -> "There was a problem loading the data from the API"
        else -> "There was some problem with data validation"
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
