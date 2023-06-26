package com.hwaryun.foodmarket.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.hwaryun.foodmarket.navigation.TopLevelDestination
import com.hwaryun.home.navigation.navigateToHomeGraph
import com.hwaryun.order.navigation.navigateToTransactionGraph
import com.hwaryun.profile.navigation.navigateToProfileGraph

@Composable
fun rememberMainAppState(
    navHostController: NavHostController = rememberNavController()
): MainAppState {
    return remember(navHostController) {
        MainAppState(navHostController)
    }
}

@Stable
class MainAppState(val navHostController: NavHostController) {
    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.values().asList()
    private val topLevelRoutes = topLevelDestinations.map { it.route }

    val shouldShowBottomBar: Boolean
        @Composable get() = navHostController
            .currentBackStackEntryAsState().value?.destination?.route in topLevelRoutes

    val currentDestination: NavDestination?
        @Composable get() = navHostController
            .currentBackStackEntryAsState().value?.destination

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        val topLevelNavOptions = navOptions {
            popUpTo(navHostController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

        when (topLevelDestination) {
            TopLevelDestination.HOME -> navHostController.navigateToHomeGraph(topLevelNavOptions)
            TopLevelDestination.TRANSACTION -> navHostController.navigateToTransactionGraph(
                topLevelNavOptions
            )

            TopLevelDestination.PROFILE -> navHostController.navigateToProfileGraph(
                topLevelNavOptions
            )

            else -> {}
        }
    }
}