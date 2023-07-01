package com.hwaryun.foodmarket.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import com.hwaryun.cart.navigation.cartGraph
import com.hwaryun.cart.navigation.cartGraphRoute
import com.hwaryun.cart.navigation.navigateToCartGraph
import com.hwaryun.cart.navigation.navigateToSuccessOrder
import com.hwaryun.cart.navigation.successOrderScreen
import com.hwaryun.food_detail.navigation.foodDetailsScreen
import com.hwaryun.food_detail.navigation.navigateToFoodDetails
import com.hwaryun.foodmarket.ui.MainAppState
import com.hwaryun.home.navigation.foodDetailsRoute
import com.hwaryun.home.navigation.homeGraph
import com.hwaryun.home.navigation.navigateToHomeGraph
import com.hwaryun.login.navigation.loginGraph
import com.hwaryun.login.navigation.loginGraphRoute
import com.hwaryun.onboarding.navigation.onBoardingScreen
import com.hwaryun.profile.navigation.profileGraph
import com.hwaryun.search.navigation.searchGraph
import com.hwaryun.signup.navigation.addressScreen
import com.hwaryun.signup.navigation.navigateToAddress
import com.hwaryun.signup.navigation.navigateToRegisterGraph
import com.hwaryun.signup.navigation.registerGraph
import com.hwaryun.transaction.navigation.navigateToTransactionGraph
import com.hwaryun.transaction.navigation.transactionGraph
import com.hwaryun.transaction_detail.navigation.navigateToTransactionDetailGraph
import com.hwaryun.transaction_detail.navigation.transactionDetailsScreen

@Composable
fun MainAppNavHost(
    modifier: Modifier = Modifier,
    mainAppState: MainAppState,
    startDestination: String,
    onShowSnackbar: suspend (String, String?) -> Boolean
) {
    val navController = mainAppState.navHostController
    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = startDestination
    ) {
        onBoardingScreen()
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
                popBackStack = navController::popBackStack,
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
                navigateToSuccessOrder = { foodName, totalPrice ->
                    navController.navigateToSuccessOrder(
                        foodName = foodName,
                        totalPrice = totalPrice,
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
                navigateToHome = { mainAppState.navigateToTopLevelDestination(TopLevelDestination.HOME) },
            )
        }
        searchGraph(
            onFoodClick = navController::navigateToFoodDetails,
            onShowSnackbar = onShowSnackbar
        )
        transactionGraph(
            onTransactionClick = navController::navigateToTransactionDetailGraph,
            onShowSnackbar = onShowSnackbar
        ) {
            transactionDetailsScreen(
                popBackStack = navController::popBackStack,
                onShowSnackbar = onShowSnackbar
            )
        }
        profileGraph(
            onShowSnackbar = onShowSnackbar,
            nestedGraphs = {}
        )
    }
}