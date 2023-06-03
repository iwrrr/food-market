package com.hwaryun.foodmarket.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.hwaryun.foodmarket.MainAppState
import com.hwaryun.home.navigation.homeGraph
import com.hwaryun.home.navigation.homeGraphRoute
import com.hwaryun.order.navigation.orderGraph
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
            onButtonClick = {
                println("onButtonClick fro navhost")
//                navController.navigateToWatchListDetails()
            },
            nestedGraphs = {
//                watchListDetailsScreen(
//                    onBackClick = navController::popBackStack,
//                    onButtonClick = {}
//                )
            }
        )
        orderGraph(
            onButtonClick = {
                println("onButtonClick fro navhost")
//                navController.navigateToWatchListDetails()
            },
            nestedGraphs = {
//                watchListDetailsScreen(
//                    onBackClick = navController::popBackStack,
//                    onButtonClick = {}
//                )
            }
        )
        profileGraph(
            onButtonClick = {
                println("onButtonClick fro navhost")
//                navController.navigateToWatchListDetails()
            },
            nestedGraphs = {
                //
            }
        )
    }
}