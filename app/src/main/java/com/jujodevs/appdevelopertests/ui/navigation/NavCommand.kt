package com.jujodevs.appdevelopertests.ui.navigation

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class NavCommand(
    internal val feature: Feature,
    internal val subRoute: String = SubRoute.HOME.route,
    navArgs: List<NavArg> = emptyList()
) {
    class ContentType(feature: Feature) : NavCommand(feature)

    class ContentDetail(feature: Feature) :
        NavCommand(feature, SubRoute.DETAIL.route, listOf(NavArg.Id)) {
        fun createRoute(id: Int) = "${feature.route}/$subRoute/$id"
    }

    val route = run {
        val args = navArgs.map { "{${it.key}}" }
        listOf(feature.route)
            .plus(subRoute)
            .plus(args)
            .joinToString("/")
    }

    val args = navArgs.map {
        navArgument(it.key) { type = it.navType }
    }
}

sealed class NavArg(val key: String, navTypeWrapper: NavTypeWrapper) {

    val navType = navTypeWrapper.navType

    data object Id : NavArg("id", NavTypeWrapper.IntType)
}

sealed class NavTypeWrapper(val navType: NavType<*>) {
    abstract fun getArg(navBackStackEntry: NavBackStackEntry, key: String): Any

    data object IntType : NavTypeWrapper(NavType.IntType) {
        override fun getArg(navBackStackEntry: NavBackStackEntry, key: String): Any =
            navBackStackEntry.arguments?.getInt(key) as Any
    }
}
