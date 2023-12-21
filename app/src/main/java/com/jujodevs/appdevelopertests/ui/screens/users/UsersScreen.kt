package com.jujodevs.appdevelopertests.ui.screens.users

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun UsersScreen(
    modifier: Modifier = Modifier,
    onNavigateToDetail: (String) -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize(),
    ) {
        Button(onClick = { onNavigateToDetail("email@example.com") }) {
            Text(text = "Go detail")
        }
    }
}
