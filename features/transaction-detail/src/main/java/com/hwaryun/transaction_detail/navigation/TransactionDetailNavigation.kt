package com.hwaryun.transaction_detail.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.composable
import com.hwaryun.transaction_detail.TransactionDetailRoute

const val TRANSACTION_ID = "transaction_id"

const val transactionDetailRoute = "transaction_detail_route/{$TRANSACTION_ID}"

fun NavController.navigateToTransactionDetailGraph(
    transactionId: Int,
    navOptions: NavOptions? = null
) {
    this.navigate("transaction_detail_route/$transactionId", navOptions)
}

@ExperimentalAnimationApi
fun NavGraphBuilder.transactionDetailsScreen(
    popBackStack: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    enterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = null,
    exitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = null,
    popEnterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = null,
    popExitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = null,
) {
    composable(
        route = transactionDetailRoute,
        arguments = listOf(
            navArgument(TRANSACTION_ID) { type = NavType.IntType },
        ),
        enterTransition = enterTransition,
        exitTransition = exitTransition,
        popEnterTransition = popEnterTransition,
        popExitTransition = popExitTransition,
    ) {
        TransactionDetailRoute(
            popBackStack = popBackStack,
            onShowSnackbar = onShowSnackbar
        )
    }
}