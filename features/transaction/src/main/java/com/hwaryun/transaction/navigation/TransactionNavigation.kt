package com.hwaryun.transaction.navigation

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
import com.hwaryun.transaction.TransactionRoute

const val transactionGraphRoute = "transaction_graph_route"
const val transactionRoute = "transaction_route"

fun NavController.navigateToTransactionGraph(navOptions: NavOptions? = null) {
    this.navigate(transactionGraphRoute, navOptions)
}

@ExperimentalAnimationApi
fun NavGraphBuilder.transactionGraph(
    onTransactionClick: (Int) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    enterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = null,
    exitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = null,
    popEnterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = null,
    popExitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = null,
    nestedGraphs: NavGraphBuilder.() -> Unit,
) {
    navigation(
        route = transactionGraphRoute,
        startDestination = transactionRoute
    ) {
        composable(
            route = transactionRoute,
            enterTransition = enterTransition,
            exitTransition = exitTransition,
            popEnterTransition = popEnterTransition,
            popExitTransition = popExitTransition,
        ) {
            TransactionRoute(
                onTransactionClick = onTransactionClick,
                onShowSnackbar = onShowSnackbar
            )
        }
        nestedGraphs()
    }
}