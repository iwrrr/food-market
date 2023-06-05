package com.hwaryun.foodmarket.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import com.hwaryun.foodmarket.MainAppState
import com.hwaryun.home.navigation.foodDetailsRoute
import com.hwaryun.home.navigation.foodDetailsScreen
import com.hwaryun.home.navigation.homeGraph
import com.hwaryun.home.navigation.homeGraphRoute
import com.hwaryun.home.navigation.navigateToFoodDetails
import com.hwaryun.order.navigation.orderGraph
import com.hwaryun.payment.navigation.navigateToPaymentGraph
import com.hwaryun.payment.navigation.navigateToSuccessOrder
import com.hwaryun.payment.navigation.paymentGraph
import com.hwaryun.payment.navigation.successOrderScreen
import com.hwaryun.profile.navigation.profileGraph

@Composable
fun MainAppNavHost(
    mainAppState: MainAppState,
    modifier: Modifier = Modifier,
    startDestination: String = homeGraphRoute
) {
    val navController = mainAppState.navHostController
    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = startDestination
    ) {
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