package com.jujodevs.appdevelopertests.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.jujodevs.appdevelopertests.ui.navigation.Navigation
import com.jujodevs.appdevelopertests.ui.theme.AppDeveloperTestsTheme

@Composable
fun DeveloperTestsApp(modifier: Modifier = Modifier) {
    val navHostController = rememberNavController()

    Scaffold(
        topBar = {},
        content = { padding ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.padding(padding),
            ) {
                Navigation(navHostController) { route ->
                    navHostController.navigate(route)
                }
            }
        },
        modifier = modifier,
    )
}

@Composable
fun DeveloperTestsScreen(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    AppDeveloperTestsTheme(
        dynamicColor = false,
    ) {
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = modifier.fillMaxSize(),
        ) {
            content()
        }
    }
}
