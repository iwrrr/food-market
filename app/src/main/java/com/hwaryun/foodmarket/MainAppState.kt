package com.hwaryun.foodmarket

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.hwaryun.foodmarket.navigation.TopLevelDestination
import com.hwaryun.home.navigation.homeGraphRoute
import com.hwaryun.home.navigation.navigateToHomeGraph
import com.hwaryun.order.navigation.navigateToOrderGraph
import com.hwaryun.order.navigation.orderGraphRoute
import com.hwaryun.profile.navigation.navigateToProfileGraph
import com.hwaryun.profile.navigation.profileGraphRoute

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
            TopLevelDestination.ORDER -> navHostController.navigateToOrderGraph(topLevelNavOptions)
            TopLevelDestination.PROFILE -> navHostController.navigateToProfileGraph(topLevelNavOptions)
        }
    }
}