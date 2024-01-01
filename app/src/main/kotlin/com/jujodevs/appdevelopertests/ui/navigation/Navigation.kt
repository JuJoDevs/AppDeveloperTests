package com.jujodevs.appdevelopertests.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.jujodevs.appdevelopertests.ui.DeveloperTestsAppState
import com.jujodevs.appdevelopertests.ui.screens.userdetail.UserDetailScreen
import com.jujodevs.appdevelopertests.ui.screens.users.UsersScreen

@Composable
fun Navigation(stateApp: DeveloperTestsAppState) {
    NavHost(
        navController = stateApp.navHostController,
        startDestination = Feature.USERS.route,
    ) {
        userNav(stateApp = stateApp)
    }
}

fun NavGraphBuilder.userNav(stateApp: DeveloperTestsAppState) {
    navigation(
        route = Feature.USERS.route,
        startDestination = NavCommand.ContentType(Feature.USERS).route,
    ) {
        composable(NavCommand.ContentType(Feature.USERS)) {
            UsersScreen(appState = stateApp)
        }
        composable(NavCommand.ContentDetail(Feature.USERS)) {
            UserDetailScreen()
        }
    }
}

fun NavGraphBuilder.composable(
    navItem: NavCommand,
    content: @Composable (NavBackStackEntry) -> Unit
) {
    composable(
        route = navItem.route,
        arguments = navItem.args,
    ) {
        content(it)
    }
}
