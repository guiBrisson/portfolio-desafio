package me.brisson.g1.screen.web

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.toRoute
import me.brisson.g1.navigation.WebRoute

class WebViewModel(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val urlRouteData: WebRoute = savedStateHandle.toRoute<WebRoute>()
    val url: String = urlRouteData.url

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val savedStateHandle = createSavedStateHandle()
                WebViewModel(savedStateHandle)
            }
        }
    }
}
