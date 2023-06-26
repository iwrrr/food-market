package com.hwaryun.payment.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.hwaryun.payment.CartRoute
import com.hwaryun.payment.SuccessOrderRoute

const val cartGraphRoute = "cart_graph_route"
const val cartRoute = "cart_route"

const val successOrderRoute = "success_order_route"

fun NavController.navigateToCartGraph(navOptions: NavOptions? = null) {
    this.navigate(cartGraphRoute, navOptions)
}

fun NavController.navigateToSuccessOrder(navOptions: NavOptions? = null) {
    this.navigate(successOrderRoute, navOptions)
}

fun NavGraphBuilder.cartGraph(
    popBackStack: () -> Unit,
    navigateToOrder: () -> Unit,
    navigateToSuccessOrder: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    navigation(
        route = cartGraphRoute,
        startDestination = cartRoute,
    ) {
        composable(
            route = cartRoute
        ) {
            CartRoute(
                popBackStack = popBackStack,
                navigateToOrder = navigateToOrder,
                navigateToSuccessOrder = navigateToSuccessOrder,
                onShowSnackbar = onShowSnackbar
            )
        }
    }
}

fun NavGraphBuilder.successOrderScreen(
    navigateToHome: () -> Unit,
) {
    composable(route = successOrderRoute) {
        SuccessOrderRoute(
            navigateToHome = navigateToHome,
        )
    }
}