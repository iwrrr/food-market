package com.hwaryun.payment.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.hwaryun.payment.PaymentRoute

const val paymentGraphRoute = "payment_graph_route"
const val orderRoute = "payment_route"

fun NavController.navigateToPaymentGraph(navOptions: NavOptions? = null) {
    this.navigate(paymentGraphRoute, navOptions)
}

fun NavGraphBuilder.paymentGraph(
    popBackStack: () -> Unit,
    navigateToSuccessOrder: () -> Unit,
    nestedGraphs: NavGraphBuilder.() -> Unit,
) {
    navigation(
        route = paymentGraphRoute,
        startDestination = orderRoute
    ) {
        composable(route = orderRoute) {
            PaymentRoute(
                popBackStack = popBackStack,
                navigateToSuccessOrder = navigateToSuccessOrder
            )
        }
        nestedGraphs()
    }
}