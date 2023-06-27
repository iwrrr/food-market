package com.hwaryun.foodmarket.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import com.hwaryun.food_detail.navigation.foodDetailsScreen
import com.hwaryun.food_detail.navigation.navigateToFoodDetails
import com.hwaryun.foodmarket.ui.MainAppState
import com.hwaryun.home.navigation.foodDetailsRoute
import com.hwaryun.home.navigation.homeGraph
import com.hwaryun.home.navigation.navigateToHomeGraph
import com.hwaryun.login.navigation.loginGraph
import com.hwaryun.login.navigation.loginGraphRoute
import com.hwaryun.order.navigation.navigateToTransactionGraph
import com.hwaryun.order.navigation.transactionGraph
import com.hwaryun.payment.navigation.cartGraph
import com.hwaryun.payment.navigation.cartGraphRoute
import com.hwaryun.payment.navigation.navigateToCartGraph
import com.hwaryun.payment.navigation.navigateToSuccessOrder
import com.hwaryun.payment.navigation.successOrderScreen
import com.hwaryun.profile.navigation.profileGraph
import com.hwaryun.search.navigation.searchGraph
import com.hwaryun.signup.navigation.addressScreen
import com.hwaryun.signup.navigation.navigateToAddress
import com.hwaryun.signup.navigation.navigateToRegisterGraph
import com.hwaryun.signup.navigation.registerGraph

@Composable
fun MainAppNavHost(
    mainAppState: MainAppState,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
    startDestination: String
) {
    val navController = mainAppState.navHostController
    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = startDestination
    ) {
        loginGraph(
            navigateToSignUpScreen = navController::navigateToRegisterGraph,
            onShowSnackbar = onShowSnackbar,
        )
        registerGraph(
            navController = navController,
            popBackStack = navController::popBackStack,
            navigateToAddressScreen = navController::navigateToAddress
        ) {
            addressScreen(
                navController = navController,
                popBackStack = navController::popBackStack,
                onShowSnackbar = onShowSnackbar,
                navigateToHomeScreen = {
                    navController.navigateToHomeGraph(
                        navOptions = navOptions {
                            popUpTo(loginGraphRoute) {
                                inclusive = true
                            }
                        }
                    )
                },
            )
        }
        homeGraph(
            onCartClick = navController::navigateToCartGraph,
            onFoodClick = navController::navigateToFoodDetails,
            onSearchClick = { mainAppState.navigateToTopLevelDestination(TopLevelDestination.SEARCH) },
        ) {
            foodDetailsScreen(
                navigateToCart = {
                    navController.navigateToCartGraph(
                        navOptions = navOptions {
                            popUpTo(foodDetailsRoute) {
                                inclusive = true
                            }
                        }
                    )
                },
                onShowSnackbar = onShowSnackbar,
            )
            cartGraph(
                popBackStack = navController::popBackStack,
                navigateToOrder = navController::navigateToTransactionGraph,
                navigateToSuccessOrder = {
                    navController.navigateToSuccessOrder(
                        navOptions = navOptions {
                            popUpTo(cartGraphRoute) {
                                inclusive = true
                            }
                        }
                    )
                },
                onShowSnackbar = onShowSnackbar,
            )
            successOrderScreen(
                navigateToHome = navController::popBackStack,
            )
        }
        searchGraph(
            navigateToHome = {},
            onFoodClick = navController::navigateToFoodDetails,
            onShowSnackbar = onShowSnackbar,
            nestedGraphs = {}
        )
        transactionGraph(
            navigateToHome = { mainAppState.navigateToTopLevelDestination(TopLevelDestination.HOME) },
            onTransactionClick = { transactionId ->
                navController.navigateToCartGraph()
            },
            nestedGraphs = {}
        )
        profileGraph(
            nestedGraphs = {},
            onShowSnackbar = onShowSnackbar
        )
    }
}