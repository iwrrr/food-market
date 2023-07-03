package com.hwaryun.home.navigation

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
import com.hwaryun.home.HomeRoute

const val homeGraphRoute = "home_graph_route"
const val homeRoute = "home_route"

const val FOOD_ID = "foodId"
const val foodDetailsRoute = "food_details_route/{$FOOD_ID}"

fun NavController.navigateToHomeGraph(navOptions: NavOptions? = null) {
    this.navigate(homeGraphRoute, navOptions)
}

@ExperimentalAnimationApi
fun NavGraphBuilder.homeGraph(
    onCartClick: () -> Unit,
    onFoodClick: (Int) -> Unit,
    onSearchClick: () -> Unit,
    enterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = null,
    exitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = null,
    popEnterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = null,
    popExitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = null,
    nestedGraphs: NavGraphBuilder.() -> Unit,
) {
    navigation(
        route = homeGraphRoute,
        startDestination = homeRoute
    ) {
        composable(
            route = homeRoute,
            enterTransition = enterTransition,
            exitTransition = exitTransition,
            popEnterTransition = popEnterTransition,
            popExitTransition = popExitTransition,
        ) {
            HomeRoute(
                onCartClick = onCartClick,
                onFoodClick = onFoodClick,
                onSearchClick = onSearchClick
            )
        }
        nestedGraphs()
    }
}