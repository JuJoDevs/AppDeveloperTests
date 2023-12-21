package com.jujodevs.appdevelopertests.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.jujodevs.appdevelopertests.ui.screens.userdetail.UserDetailScreen
import com.jujodevs.appdevelopertests.ui.screens.users.UsersScreen

@Composable
fun Navigation(
    navHostController: NavHostController,
    onNavigationDetailClick: (String) -> Unit
) {
    NavHost(
        navController = navHostController,
        startDestination = Feature.USERS.route,
    ) {
        userNav(onNavigationDetailClick)
    }
}

fun NavGraphBuilder.userNav(
    onNavigationDetailClick: (String) -> Unit
) {
    navigation(
        route = Feature.USERS.route,
        startDestination = NavCommand.ContentType(Feature.USERS).route,
    ) {
        composable(NavCommand.ContentType(Feature.USERS)) {
            UsersScreen(
                onNavigateToDetail = { email ->
                    onNavigationDetailClick(
                        NavCommand.ContentDetail(Feature.USERS).createRoute(email),
                    )
                },
            )
        }
        composable(NavCommand.ContentDetail(Feature.USERS)) { backStackEntry ->
            backStackEntry.arguments?.getString("email")?.let {
                UserDetailScreen(it)
            }
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
