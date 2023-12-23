package com.jujodevs.appdevelopertests.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.jujodevs.appdevelopertests.domain.User
import com.jujodevs.appdevelopertests.ui.screens.userdetail.UserDetailScreen
import com.jujodevs.appdevelopertests.ui.screens.users.UsersScreen

@Composable
fun Navigation(
    navHostController: NavHostController,
    onNavigationDetailClick: (User) -> Unit
) {
    NavHost(
        navController = navHostController,
        startDestination = Feature.USERS.route,
    ) {
        userNav(onNavigationDetailClick)
    }
}

fun NavGraphBuilder.userNav(
    onNavigationDetailClick: (User) -> Unit
) {
    navigation(
        route = Feature.USERS.route,
        startDestination = NavCommand.ContentType(Feature.USERS).route,
    ) {
        composable(NavCommand.ContentType(Feature.USERS)) {
            UsersScreen(
                onNavigateToDetail = { user ->
                    onNavigationDetailClick(user)
                },
            )
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
