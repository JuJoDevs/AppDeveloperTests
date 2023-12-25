package com.jujodevs.appdevelopertests.ui.common

import android.annotation.SuppressLint
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewMap(
    iFrame: String,
    modifier: Modifier = Modifier,
    webViewClient: WebViewClient = WebViewClient()
) {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                this.webViewClient = webViewClient
                settings.loadWithOverviewMode = true
                settings.useWideViewPort = true
                settings.setSupportZoom(true)
            }
        },
        update = { webView ->
            webView.loadData(iFrame, "text/html", "UTF-8")
        },
        modifier = modifier
            .fillMaxWidth(),
    )
}

fun buildIFrameMap(latitude: String, longitude: String) =
    "<iframe src=\"https://maps.google.com/maps?q=$latitude,$longitude&z=18&hl=es&output=embed" +
        "\" width=\"100%\" height=\"450\" style=\"border:0;\" allowfullscreen=\"\" " +
        "loading=\"lazy\" referrerpolicy=\"no-referrer-when-downgrade\"></iframe>"
