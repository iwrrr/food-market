package com.hwaryun.foodmarket.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navOptions
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.hwaryun.datasource.util.NetworkMonitor
import com.hwaryun.foodmarket.navigation.TopLevelDestination
import com.hwaryun.home.navigation.navigateToHomeGraph
import com.hwaryun.profile.navigation.navigateToProfileGraph
import com.hwaryun.search.navigation.navigateToSearchGraph
import com.hwaryun.transaction.navigation.navigateToTransactionGraph
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@ExperimentalAnimationApi
@Composable
fun rememberMainAppState(
    navHostController: NavHostController = rememberAnimatedNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    networkMonitor: NetworkMonitor,
): MainAppState {
    return remember(navHostController, coroutineScope, networkMonitor) {
        MainAppState(navHostController, coroutineScope, networkMonitor)
    }
}

@Stable
class MainAppState(
    val navHostController: NavHostController,
    coroutineScope: CoroutineScope,
    networkMonitor: NetworkMonitor
) {
    val currentDestination: NavDestination?
        @Composable get() = navHostController
            .currentBackStackEntryAsState().value?.destination

    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.values().asList()
    private val topLevelRoutes = topLevelDestinations.map { it.route }

    val shouldShowBottomBar: Boolean
        @Composable get() = navHostController
            .currentBackStackEntryAsState().value?.destination?.route in topLevelRoutes

    val isOffline = networkMonitor.isOnline
        .map(Boolean::not)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false
        )

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
            TopLevelDestination.SEARCH -> navHostController.navigateToSearchGraph(topLevelNavOptions)
            TopLevelDestination.TRANSACTION -> navHostController.navigateToTransactionGraph(
                topLevelNavOptions
            )

            TopLevelDestination.PROFILE -> navHostController.navigateToProfileGraph(
                topLevelNavOptions
            )
        }
    }
}