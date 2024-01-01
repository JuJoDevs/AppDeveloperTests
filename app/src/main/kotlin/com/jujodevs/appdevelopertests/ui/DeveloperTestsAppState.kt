package com.jujodevs.appdevelopertests.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jujodevs.appdevelopertests.domain.User
import com.jujodevs.appdevelopertests.ui.navigation.Feature
import com.jujodevs.appdevelopertests.ui.navigation.NavCommand

@Composable
fun rememberDeveloperTestsAppState(
    navHostController: NavHostController = rememberNavController(),
    currentUser: MutableState<User> = remember { mutableStateOf(User()) },
    findText: MutableState<String> = remember { mutableStateOf("") }
): DeveloperTestsAppState =
    remember(navHostController, currentUser, findText) {
        DeveloperTestsAppState(navHostController, currentUser, findText)
    }

class DeveloperTestsAppState(
    val navHostController: NavHostController,
    val currentUser: MutableState<User>,
    val findText: MutableState<String>
) {
    val expandedAnimationTime = 2000

    val currentRoute: String
        @Composable get() =
            navHostController.currentBackStackEntryAsState().value?.destination?.route ?: ""

    fun navToDetail(user: User) =
        navHostController.navigate(NavCommand.ContentDetail(Feature.USERS).createRoute(user.id))

    fun popBackStack() = navHostController.popBackStack()
}
