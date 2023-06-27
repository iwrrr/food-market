package com.hwaryun.transaction.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.hwaryun.transaction.TransactionRoute

const val transactionGraphRoute = "transaction_graph_route"
const val transactionRoute = "transaction_route"

fun NavController.navigateToTransactionGraph(navOptions: NavOptions? = null) {
    this.navigate(transactionGraphRoute, navOptions)
}

fun NavGraphBuilder.transactionGraph(
    onTransactionClick: (Int) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    nestedGraphs: NavGraphBuilder.() -> Unit,
) {
    navigation(
        route = transactionGraphRoute,
        startDestination = transactionRoute
    ) {
        composable(route = transactionRoute) {
            TransactionRoute(
                onTransactionClick = onTransactionClick,
                onShowSnackbar = onShowSnackbar
            )
        }
        nestedGraphs()
    }
}