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
import com.hwaryun.order.navigation.navigateToOrderGraph
import com.hwaryun.order.navigation.orderGraph
import com.hwaryun.payment.navigation.navigateToPaymentGraph
import com.hwaryun.payment.navigation.navigateToSuccessOrder
import com.hwaryun.payment.navigation.paymentGraph
import com.hwaryun.payment.navigation.successOrderScreen
import com.hwaryun.profile.navigation.profileGraph
import com.hwaryun.signin.navigation.signInGraph
import com.hwaryun.signin.navigation.signInGraphRoute
import com.hwaryun.signup.navigation.addressScreen
import com.hwaryun.signup.navigation.navigateToAddress
import com.hwaryun.signup.navigation.navigateToSignUpGraph
import com.hwaryun.signup.navigation.signUpGraph

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
        signInGraph(
            navigateToSignUpScreen = navController::navigateToSignUpGraph,
            onShowSnackbar = onShowSnackbar,
        )
        signUpGraph(
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
                            popUpTo(signInGraphRoute) {
                                inclusive = true
                            }
                        }
                    )
                },
            )
        }
        homeGraph(
            onFoodClick = navController::navigateToFoodDetails,
        ) {
            foodDetailsScreen(
                onOrderClick = { foodId, qty, total ->
                    navController.navigateToPaymentGraph(
                        foodId = foodId,
                        qty = qty,
                        total = total
                    )
                }
            )
            paymentGraph(
                popBackStack = navController::popBackStack,
                navigateToOrder = navController::navigateToOrderGraph,
                navigateToSuccessOrder = {
                    navController.navigateToSuccessOrder(
                        navOptions = navOptions {
                            popUpTo(foodDetailsRoute) {
                                inclusive = true
                            }
                        }
                    )
                },
                onShowSnackbar = onShowSnackbar,
            )
            successOrderScreen(
                navigateToHome = navController::popBackStack,
            ) {
                navController.popBackStack()
                mainAppState.navigateToTopLevelDestination(TopLevelDestination.ORDER)
            }
        }
        orderGraph(
            navigateToHome = { mainAppState.navigateToTopLevelDestination(TopLevelDestination.HOME) },
            onOrderClick = { transactionId ->
                navController.navigateToPaymentGraph(
                    transactionId = transactionId
                )
            },
            nestedGraphs = {}
        )
        profileGraph(
            nestedGraphs = {},
            onShowSnackbar = onShowSnackbar
        )
    }
}