package com.hwaryun.search.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation
import com.hwaryun.search.SearchRoute

const val searchGraphRoute = "search_graph_route"
const val searchRoute = "search_route"

fun NavController.navigateToSearchGraph(navOptions: NavOptions? = null) {
    this.navigate(searchGraphRoute, navOptions)
}

@ExperimentalAnimationApi
fun NavGraphBuilder.searchGraph(
    onFoodClick: (Int) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    enterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = null,
    exitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = null,
    popEnterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = null,
    popExitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = null,
) {
    navigation(
        route = searchGraphRoute,
        startDestination = searchRoute
    ) {
        composable(
            route = searchRoute,
            enterTransition = enterTransition,
            exitTransition = exitTransition,
            popEnterTransition = popEnterTransition,
            popExitTransition = popExitTransition,
        ) {
            SearchRoute(
                onFoodClick = onFoodClick,
                onShowSnackbar = onShowSnackbar,
            )
        }
    }
}