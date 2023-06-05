package com.hwaryun.foodmarket.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import com.hwaryun.foodmarket.MainAppState
import com.hwaryun.home.navigation.foodDetailsRoute
import com.hwaryun.home.navigation.foodDetailsScreen
import com.hwaryun.home.navigation.homeGraph
import com.hwaryun.home.navigation.navigateToFoodDetails
import com.hwaryun.home.navigation.navigateToHomeGraph
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
    modifier: Modifier = Modifier,
    startDestination: String = signInGraphRoute
) {
    val navController = mainAppState.navHostController
    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = startDestination
    ) {
        signInGraph(
            navigateToSignUpScreen = navController::navigateToSignUpGraph,
        ) {
            navController.navigateToHomeGraph(
                navOptions = navOptions {
                    popUpTo(signInGraphRoute) {
                        inclusive = true
                    }
                }
            )
        }
        signUpGraph(
            popBackStack = navController::popBackStack,
            navigateToAddressScreen = navController::navigateToAddress,
            nestedGraphs = {
                addressScreen(
                    popBackStack = navController::popBackStack
                ) {
                    navController.navigateToHomeGraph(
                        navOptions = navOptions {
                            popUpTo(signInGraphRoute) {
                                inclusive = true
                            }
                        }
                    )
                }
            }
        )
        homeGraph(
            onFoodClick = navController::navigateToFoodDetails,
            nestedGraphs = {
                foodDetailsScreen(
                    onOrderClick = navController::navigateToPaymentGraph
                )
                paymentGraph(
                    popBackStack = navController::popBackStack,
                    navigateToSuccessOrder = {
                        navController.navigateToSuccessOrder(
                            navOptions = navOptions {
                                popUpTo(foodDetailsRoute) {
                                    inclusive = true
                                }
                            }
                        )
                    }
                )
                successOrderScreen(
                    navigateToHome = navController::popBackStack,
                    navigateToOrder = {
                        mainAppState.navigateToTopLevelDestination(TopLevelDestination.ORDER)
                    }
                )
            }
        )
        orderGraph(
            onOrderClick = {},
            nestedGraphs = {}
        )
        profileGraph(
            onButtonClick = {},
            nestedGraphs = {}
        )
    }
}