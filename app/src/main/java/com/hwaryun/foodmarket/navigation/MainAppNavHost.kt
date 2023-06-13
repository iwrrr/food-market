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
import com.hwaryun.order.navigation.orderGraph
import com.hwaryun.payment.navigation.navigateToPaymentGraph
import com.hwaryun.payment.navigation.navigateToSuccessOrder
import com.hwaryun.payment.navigation.paymentGraph
import com.hwaryun.payment.navigation.successOrderScreen
import com.hwaryun.profile.navigation.profileGraph
import com.hwaryun.profile.navigation.profileGraphRoute
import com.hwaryun.signin.navigation.navigateToSignInGraph
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
        signUpGraph(
            navController = navController,
            popBackStack = navController::popBackStack,
            navigateToAddressScreen = navController::navigateToAddress,
            nestedGraphs = {
                addressScreen(
                    navController = navController,
                    popBackStack = navController::popBackStack,
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
        )
        homeGraph(
            onFoodClick = navController::navigateToFoodDetails
        ) {
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
                    navController.popBackStack()
                    mainAppState.navigateToTopLevelDestination(TopLevelDestination.ORDER)
                }
            )
        }
        orderGraph(
            onOrderClick = {},
            nestedGraphs = {}
        )
        profileGraph(
            onLogoutClick = {
                navController.navigateToSignInGraph(
                    navOptions = navOptions {
                        popUpTo(profileGraphRoute) {
                            inclusive = true
                        }
                    }
                )
            },
            nestedGraphs = {}
        )
    }
}