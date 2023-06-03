package com.hwaryun.order.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.hwaryun.order.OrderRoute

const val orderGraphRoute = "order_graph_route"
const val orderRoute = "order_route"

fun NavController.navigateToOrderGraph(navOptions: NavOptions? = null) {
    this.navigate(orderGraphRoute, navOptions)
}

fun NavGraphBuilder.orderGraph(
    onButtonClick: () -> Unit,
    nestedGraphs: NavGraphBuilder.() -> Unit,
) {
    navigation(
        route = orderGraphRoute,
        startDestination = orderRoute
    ) {
        composable(route = orderRoute) {
            OrderRoute(onButtonClick)
        }
        nestedGraphs()
    }
}