package com.hwaryun.login.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation
import com.hwaryun.login.LoginRoute

const val loginGraphRoute = "login_graph_route"
const val loginRoute = "login_route"

@ExperimentalAnimationApi
fun NavGraphBuilder.loginGraph(
    navigateToSignUpScreen: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    enterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = null,
    exitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = null,
    popEnterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = null,
    popExitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = null,
) {
    navigation(
        route = loginGraphRoute,
        startDestination = loginRoute
    ) {
        composable(
            route = loginRoute,
            enterTransition = enterTransition,
            exitTransition = exitTransition,
            popEnterTransition = popEnterTransition,
            popExitTransition = popExitTransition,
        ) {
            LoginRoute(
                navigateToSignUpScreen = navigateToSignUpScreen,
                onShowSnackbar = onShowSnackbar,
            )
        }
    }
}