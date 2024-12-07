package me.brisson.g1.ui.components

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun WebViewComponent(
    modifier: Modifier = Modifier,
    url: String,
    onBack: () -> Unit,
) {
    val context = LocalContext.current
    val webView = remember { WebView(context) }
    var isLoading by remember { mutableStateOf(false) }
    var canGoBack by remember { mutableStateOf(false) }

    LaunchedEffect(webView) {
        webView.apply {
            webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    if (!canGoBack) {
                        isLoading = true
                        canGoBack = view?.canGoBack() ?: false
                    }
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    isLoading = false
                }
            }
            @SuppressLint("SetJavaScriptEnabled")
            settings.javaScriptEnabled = true

            loadUrl(url)
        }
    }

    BackHandler(enabled = canGoBack) {
        if (webView.canGoBack()) webView.goBack() else onBack()
    }

    Box(modifier = modifier then Modifier) {
        AndroidView(
            modifier = Modifier,
            factory = {
                // We need to wrap the webView inside a frame layout, because for some reason
                // the webView take up the whole screen space while it is loading.
                // See here: https://slack-chats.kotlinlang.org/t/16312224/i-have-strange-behaviour-of-one-of-my-screens-when-i-added-a
                FrameLayout(context).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    addView(webView)
                }
            },
        )

        if (isLoading) LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
    }
}
