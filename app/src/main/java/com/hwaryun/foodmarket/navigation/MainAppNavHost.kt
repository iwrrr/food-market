package com.hwaryun.foodmarket.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.navOptions
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.hwaryun.cart.navigation.cartGraph
import com.hwaryun.cart.navigation.cartGraphRoute
import com.hwaryun.cart.navigation.cartRoute
import com.hwaryun.cart.navigation.navigateToCartGraph
import com.hwaryun.cart.navigation.navigateToSuccessOrder
import com.hwaryun.cart.navigation.successOrderRoute
import com.hwaryun.cart.navigation.successOrderScreen
import com.hwaryun.designsystem.utils.enterTransition
import com.hwaryun.designsystem.utils.exitTransition
import com.hwaryun.designsystem.utils.popEnterTransition
import com.hwaryun.designsystem.utils.popExitTransition
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
import com.hwaryun.transaction_detail.navigation.transactionDetailRoute
import com.hwaryun.transaction_detail.navigation.transactionDetailsScreen

@ExperimentalAnimationApi
@Composable
fun MainAppNavHost(
    modifier: Modifier = Modifier,
    mainAppState: MainAppState,
    startDestination: String,
    onShowSnackbar: suspend (String, String?) -> Boolean
) {
    val navController = mainAppState.navHostController
    AnimatedNavHost(
        navController = navController,
        modifier = modifier,
        startDestination = startDestination
    ) {
        onBoardingScreen()
        loginGraph(
            navigateToSignUpScreen = navController::navigateToRegisterGraph,
            onShowSnackbar = onShowSnackbar
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
            enterTransition = {
                when (initialState.destination.route) {
                    foodDetailsRoute -> enterTransition
                    cartRoute -> enterTransition
                    successOrderRoute -> enterTransition
                    else -> fadeIn(tween(300))
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    foodDetailsRoute -> exitTransition
                    cartRoute -> exitTransition
                    successOrderRoute -> exitTransition
                    else -> fadeOut(tween(300))
                }
            },
            popEnterTransition = {
                when (initialState.destination.route) {
                    foodDetailsRoute -> popEnterTransition
                    cartRoute -> popEnterTransition
                    successOrderRoute -> popEnterTransition
                    else -> fadeIn(tween(300))
                }
            },
            popExitTransition = { fadeOut(tween(300)) }
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
                enterTransition = { enterTransition },
                popExitTransition = { popExitTransition }
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
                enterTransition = { enterTransition },
                popExitTransition = { popExitTransition }
            )
            successOrderScreen(
                navigateToHome = { mainAppState.navigateToTopLevelDestination(TopLevelDestination.HOME) },
            )
        }
        searchGraph(
            onFoodClick = navController::navigateToFoodDetails,
            onShowSnackbar = onShowSnackbar,
            enterTransition = {
                when (initialState.destination.route) {
                    foodDetailsRoute -> enterTransition
                    cartRoute -> enterTransition
                    else -> fadeIn(tween(300))
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    foodDetailsRoute -> exitTransition
                    cartRoute -> exitTransition
                    else -> fadeOut(tween(300))
                }
            },
            popEnterTransition = {
                when (initialState.destination.route) {
                    foodDetailsRoute -> popEnterTransition
                    cartRoute -> popEnterTransition
                    else -> fadeIn(tween(300))
                }
            },
            popExitTransition = { fadeOut(tween(300)) }
        )
        transactionGraph(
            onTransactionClick = navController::navigateToTransactionDetailGraph,
            onShowSnackbar = onShowSnackbar,
            enterTransition = {
                when (initialState.destination.route) {
                    transactionDetailRoute -> enterTransition
                    else -> fadeIn(tween(300))
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    transactionDetailRoute -> exitTransition
                    else -> fadeOut(tween(300))
                }
            },
            popEnterTransition = {
                when (initialState.destination.route) {
                    transactionDetailRoute -> popEnterTransition
                    else -> fadeIn(tween(300))
                }
            },
            popExitTransition = { fadeOut(tween(300)) }
        ) {
            transactionDetailsScreen(
                popBackStack = navController::popBackStack,
                onShowSnackbar = onShowSnackbar,
                enterTransition = { enterTransition },
                popExitTransition = { popExitTransition }
            )
        }
        profileGraph(
            onShowSnackbar = onShowSnackbar,
            enterTransition = { fadeIn(tween(300)) },
            exitTransition = { fadeOut(tween(300)) },
            popEnterTransition = { fadeIn(tween(300)) },
            popExitTransition = { fadeOut(tween(300)) },
            nestedGraphs = {}
        )
    }
}