package me.brisson.g1.screen.web

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import me.brisson.g1.ui.components.WebAppBar
import me.brisson.g1.ui.components.WebViewComponent

@Composable
fun WebRouter(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
) {
    val viewModel: WebViewModel = viewModel<WebViewModel>(
        factory = WebViewModel.Factory
    )

    WebScreen(
        modifier = modifier,
        url = viewModel.url,
        onBack = onBack,
    )
}

@Composable
internal fun WebScreen(
    modifier: Modifier = Modifier,
    url: String,
    onBack: () -> Unit,
) {
    Column(modifier = modifier then Modifier.fillMaxSize()) {
        WebAppBar(onBack = onBack)

        WebViewComponent(
            url = url,
            onBack = onBack,
        )
    }
}
