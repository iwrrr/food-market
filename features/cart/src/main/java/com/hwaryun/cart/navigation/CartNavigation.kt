package com.hwaryun.cart.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.hwaryun.cart.CartRoute
import com.hwaryun.cart.SuccessOrderRoute
import com.hwaryun.common.ext.orDash
import com.hwaryun.common.ext.orZero

const val cartGraphRoute = "cart_graph_route"
const val cartRoute = "cart_route"

const val FOOD_NAME = "food_name"
const val TOTAL_PRICE = "total_price"

const val successOrderRoute =
    "success_order_route?$FOOD_NAME={$FOOD_NAME}&$TOTAL_PRICE={$TOTAL_PRICE}"

fun NavController.navigateToCartGraph(navOptions: NavOptions? = null) {
    this.navigate(cartGraphRoute, navOptions)
}

fun NavController.navigateToSuccessOrder(
    foodName: String,
    totalPrice: Int,
    navOptions: NavOptions? = null
) {
    this.navigate("success_order_route?$FOOD_NAME=$foodName&$TOTAL_PRICE=$totalPrice", navOptions)
}

fun NavGraphBuilder.cartGraph(
    popBackStack: () -> Unit,
    navigateToOrder: () -> Unit,
    navigateToSuccessOrder: (foodName: String, totalPrice: Int) -> Unit,
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
    composable(
        route = successOrderRoute,
        arguments = listOf(
            navArgument(FOOD_NAME) {
                type = NavType.StringType
                nullable = true
            },
            navArgument(TOTAL_PRICE) {
                type = NavType.IntType
                defaultValue = 0
            },
        )
    ) { backStackEntry ->
        val foodName = backStackEntry.arguments?.getString(FOOD_NAME).orDash()
        val totalPrice = backStackEntry.arguments?.getInt(TOTAL_PRICE).orZero()
        SuccessOrderRoute(
            foodName = foodName,
            totalPrice = totalPrice,
            navigateToHome = navigateToHome,
        )
    }
}