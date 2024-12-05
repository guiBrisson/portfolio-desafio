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
import me.brisson.g1.core.model.Feed

class FeedViewModel(
    private val feedRepository: FeedRepository,
): ViewModel() {
    private val _uiState = MutableStateFlow<FeedUiState>(FeedUiState.Loading)
    val uiState: StateFlow<FeedUiState> = _uiState.asStateFlow()

    fun fetchFeed() {
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

sealed interface FeedUiState {
    data object Loading : FeedUiState
    data class Success(val feed: Feed) : FeedUiState
    data class Error(val message: String) : FeedUiState
}
