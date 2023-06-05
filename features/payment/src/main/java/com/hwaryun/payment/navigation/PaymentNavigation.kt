package com.hwaryun.payment.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.hwaryun.payment.PaymentRoute
import com.hwaryun.payment.SuccessOrderRoute

const val paymentGraphRoute = "payment_graph_route"
const val paymentRoute = "payment_route"
const val successOrderRoute = "success_order_route"

fun NavController.navigateToPaymentGraph(navOptions: NavOptions? = null) {
    this.navigate(paymentGraphRoute, navOptions)
}

fun NavController.navigateToSuccessOrder(navOptions: NavOptions? = null) {
    this.navigate(successOrderRoute, navOptions)
}

fun NavGraphBuilder.paymentGraph(
    popBackStack: () -> Unit,
    navigateToSuccessOrder: () -> Unit,
) {
    navigation(
        route = paymentGraphRoute,
        startDestination = paymentRoute
    ) {
        composable(route = paymentRoute) {
            PaymentRoute(
                popBackStack = popBackStack,
                navigateToSuccessOrder = navigateToSuccessOrder
            )
        }
    }
}

fun NavGraphBuilder.successOrderScreen(
    navigateToHome: () -> Unit,
    navigateToOrder: () -> Unit,
) {
    composable(route = successOrderRoute) {
        SuccessOrderRoute(
            navigateToHome = navigateToHome,
            navigateToOrder = navigateToOrder,
        )
    }
}