package com.hwaryun.profile.navigation

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
import com.hwaryun.profile.ProfileRoute

const val profileGraphRoute = "profile_graph_route"
const val profileRoute = "profile_route"

fun NavController.navigateToProfileGraph(navOptions: NavOptions? = null) {
    this.navigate(profileGraphRoute, navOptions)
}

@ExperimentalAnimationApi
fun NavGraphBuilder.profileGraph(
    navigateToTransaction: () -> Unit,
    navigateToEditProfile: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    enterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = null,
    exitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = null,
    popEnterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = null,
    popExitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = null,
    nestedGraphs: NavGraphBuilder.() -> Unit,
) {
    navigation(
        route = profileGraphRoute,
        startDestination = profileRoute
    ) {
        composable(
            route = profileRoute,
            enterTransition = enterTransition,
            exitTransition = exitTransition,
            popEnterTransition = popEnterTransition,
            popExitTransition = popExitTransition,
        ) {
            ProfileRoute(
                navigateToTransaction = navigateToTransaction,
                navigateToEditProfile = navigateToEditProfile,
                onShowSnackbar = onShowSnackbar
            )
        }
        nestedGraphs()
    }
}