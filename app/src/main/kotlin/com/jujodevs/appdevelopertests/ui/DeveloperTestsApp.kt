package com.jujodevs.appdevelopertests.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jujodevs.appdevelopertests.ui.navigation.Feature
import com.jujodevs.appdevelopertests.ui.navigation.NavCommand
import com.jujodevs.appdevelopertests.ui.navigation.Navigation
import com.jujodevs.appdevelopertests.ui.screens.userdetail.UserDetailTopBar
import com.jujodevs.appdevelopertests.ui.screens.users.UsersTopBar
import com.jujodevs.appdevelopertests.ui.theme.AppDeveloperTestsTheme

@Composable
fun DeveloperTestsApp(
    modifier: Modifier = Modifier,
    stateApp: DeveloperTestsAppState = rememberDeveloperTestsAppState(),
    onFinishApp: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopBar(
                stateApp = stateApp,
                onFinishApp = onFinishApp,
            )
        },
        content = { padding ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.padding(padding),
            ) {
                Navigation(stateApp = stateApp)
            }
        },
        modifier = modifier,
    )
}

@Composable
private fun TopBar(
    stateApp: DeveloperTestsAppState,
    modifier: Modifier = Modifier,
    onFinishApp: () -> Unit
) {
    Box(
        modifier = modifier
            .animateContentSize(tween(stateApp.expandedAnimationTime))
            .height(
                if (stateApp.currentRoute == NavCommand.ContentDetail(Feature.USERS).route) {
                    242.dp
                } else {
                    88.dp
                },
            ),
    ) {
        when (stateApp.currentRoute) {
            NavCommand.ContentType(Feature.USERS).route -> {
                UsersTopBar(
                    stateApp = stateApp,
                    onNavigateBack = onFinishApp,
                )
            }
            NavCommand.ContentDetail(Feature.USERS).route -> {
                UserDetailTopBar(stateApp = stateApp)
            }
        }
    }
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
