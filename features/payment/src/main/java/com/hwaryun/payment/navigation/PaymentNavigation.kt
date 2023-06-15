package com.hwaryun.payment.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.hwaryun.payment.PaymentRoute
import com.hwaryun.payment.SuccessOrderRoute

const val TRANSACTION_ID = "transaction_id"
const val FOOD_ID = "food_id"
const val QTY = "qty"
const val TOTAL = "total"

const val paymentGraphRoute = "payment_graph_route?$FOOD_ID={$FOOD_ID}&$QTY={$QTY}&$TOTAL={$TOTAL}&$TRANSACTION_ID={$TRANSACTION_ID}"
const val paymentRoute = "payment_route"

const val successOrderRoute = "success_order_route"

fun NavController.navigateToPaymentGraph(
    foodId: Int? = null,
    qty: Int? = null,
    total: Int? = null,
    transactionId: Int? = null,
    navOptions: NavOptions? = null
) {
    if (transactionId == null) {
        this.navigate("payment_graph_route?$FOOD_ID=$foodId&$QTY=$qty&$TOTAL=$total", navOptions)
    } else {
        this.navigate("payment_graph_route?$TRANSACTION_ID=$transactionId", navOptions)
    }
}

fun NavController.navigateToSuccessOrder(navOptions: NavOptions? = null) {
    this.navigate(successOrderRoute, navOptions)
}

fun NavGraphBuilder.paymentGraph(
    popBackStack: () -> Unit,
    navigateToOrder: () -> Unit,
    navigateToSuccessOrder: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    navigation(
        route = paymentGraphRoute,
        startDestination = paymentRoute,
    ) {
        composable(
            route = paymentRoute,
            arguments = listOf(
                navArgument(TRANSACTION_ID) {
                    nullable = true
                    defaultValue = null
                },
                navArgument(FOOD_ID) {
                    nullable = true
                    defaultValue = null
                },
                navArgument(QTY) {
                    nullable = true
                    defaultValue = null
                },
                navArgument(TOTAL) {
                    nullable = true
                    defaultValue = null
                },
            )
        ) {
            PaymentRoute(
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
    navigateToOrder: () -> Unit,
) {
    composable(route = successOrderRoute) {
        SuccessOrderRoute(
            navigateToHome = navigateToHome,
            navigateToOrder = navigateToOrder,
        )
    }
}