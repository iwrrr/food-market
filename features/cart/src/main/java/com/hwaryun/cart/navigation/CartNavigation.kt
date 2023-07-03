package com.hwaryun.cart.navigation

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
import com.google.accompanist.navigation.animation.navigation
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

@ExperimentalAnimationApi
fun NavGraphBuilder.cartGraph(
    popBackStack: () -> Unit,
    navigateToOrder: () -> Unit,
    navigateToSuccessOrder: (foodName: String, totalPrice: Int) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    enterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = null,
    exitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = null,
    popEnterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = null,
    popExitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = null,
) {
    navigation(
        route = cartGraphRoute,
        startDestination = cartRoute,
    ) {
        composable(
            route = cartRoute,
            enterTransition = enterTransition,
            exitTransition = exitTransition,
            popEnterTransition = popEnterTransition,
            popExitTransition = popExitTransition,
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

@ExperimentalAnimationApi
fun NavGraphBuilder.successOrderScreen(
    navigateToHome: () -> Unit,
    enterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = null,
    exitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = null,
    popEnterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = null,
    popExitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = null,
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
        ),
        enterTransition = enterTransition,
        exitTransition = exitTransition,
        popEnterTransition = popEnterTransition,
        popExitTransition = popExitTransition,
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