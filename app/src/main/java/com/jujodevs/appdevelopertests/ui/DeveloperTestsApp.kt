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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jujodevs.appdevelopertests.domain.User
import com.jujodevs.appdevelopertests.ui.navigation.Feature
import com.jujodevs.appdevelopertests.ui.navigation.NavCommand
import com.jujodevs.appdevelopertests.ui.navigation.Navigation
import com.jujodevs.appdevelopertests.ui.screens.userdetail.UserDetailTopBar
import com.jujodevs.appdevelopertests.ui.screens.users.UsersTopBar
import com.jujodevs.appdevelopertests.ui.theme.AppDeveloperTestsTheme

private const val ExpandedAnimationTime = 2000

@Composable
fun DeveloperTestsApp(
    modifier: Modifier = Modifier,
    onFinishApp: () -> Unit = {}
) {
    val navHostController = rememberNavController()
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: ""
    var currentUser by remember { mutableStateOf(User()) }
    var findText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopBar(
                currentRoute = currentRoute,
                currentUser = currentUser,
                onBack = { navHostController.popBackStack() },
                findText = findText,
                onFindChange = { findText = it },
                onFinishApp = onFinishApp,
            )
        },
        content = { padding ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.padding(padding),
            ) {
                Navigation(navHostController, findText) { user ->
                    currentUser = user
                    navHostController.navigate(
                        NavCommand.ContentDetail(Feature.USERS).createRoute(user.id),
                    )
                }
            }
        },
        modifier = modifier,
    )
}

@Composable
private fun TopBar(
    currentRoute: String?,
    currentUser: User,
    onBack: () -> Unit,
    findText: String,
    onFindChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    onFinishApp: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = currentRoute) {
        when (currentRoute) {
            NavCommand.ContentType(Feature.USERS).route -> { expanded = false }
            NavCommand.ContentDetail(Feature.USERS).route -> { expanded = true }
        }
    }

    Box(
        modifier = modifier
            .animateContentSize(tween(ExpandedAnimationTime))
            .height(if (expanded) 242.dp else 88.dp),
    ) {
        when (currentRoute) {
            NavCommand.ContentType(Feature.USERS).route -> {
                UsersTopBar(
                    findText = findText,
                    onFindChange = onFindChange,
                    onNavigateBack = onFinishApp,
                )
            }
            NavCommand.ContentDetail(Feature.USERS).route -> {
                UserDetailTopBar(user = currentUser) {
                    onBack()
                }
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
