package com.hwaryun.order.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.hwaryun.order.OrderRoute

const val transactionGraphRoute = "transaction_graph_route"
const val transactionRoute = "transaction_route"

fun NavController.navigateToOrderGraph(navOptions: NavOptions? = null) {
    this.navigate(transactionGraphRoute, navOptions)
}

fun NavGraphBuilder.transactionGraph(
    navigateToHome: () -> Unit,
    onOrderClick: (Int) -> Unit,
    nestedGraphs: NavGraphBuilder.() -> Unit,
) {
    navigation(
        route = transactionGraphRoute,
        startDestination = transactionRoute
    ) {
        composable(route = transactionRoute) {
            OrderRoute(
                navigateToHome = navigateToHome,
                onOrderClick = onOrderClick
            )
        }
        nestedGraphs()
    }
}