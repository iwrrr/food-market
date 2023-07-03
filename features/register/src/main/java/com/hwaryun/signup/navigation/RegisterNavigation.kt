package com.hwaryun.signup.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation
import com.hwaryun.signup.AddressRoute
import com.hwaryun.signup.RegisterRoute
import com.hwaryun.signup.RegisterViewModel

const val registerGraphRoute = "register_graph_route"
const val registerRoute = "register_route"
const val addressRoute = "address_route"

fun NavController.navigateToRegisterGraph(navOptions: NavOptions? = null) {
    this.navigate(registerGraphRoute, navOptions)
}

fun NavController.navigateToAddress(navOptions: NavOptions? = null) {
    this.navigate(addressRoute, navOptions)
}

@ExperimentalAnimationApi
fun NavGraphBuilder.registerGraph(
    navController: NavHostController,
    popBackStack: () -> Unit,
    navigateToAddressScreen: () -> Unit,
    enterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = null,
    exitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = null,
    popEnterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = null,
    popExitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = null,
    nestedGraphs: NavGraphBuilder.() -> Unit
) {
    navigation(
        route = registerGraphRoute,
        startDestination = registerRoute
    ) {
        composable(
            route = registerRoute,
            enterTransition = enterTransition,
            exitTransition = exitTransition,
            popEnterTransition = popEnterTransition,
            popExitTransition = popExitTransition,
        ) { entry ->
            val viewModel = entry.sharedViewModel<RegisterViewModel>(navController = navController)
            RegisterRoute(
                popBackStack = popBackStack,
                navigateToAddressScreen = navigateToAddressScreen,
                viewModel = viewModel
            )
        }
        nestedGraphs()
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.addressScreen(
    navController: NavHostController,
    popBackStack: () -> Unit,
    navigateToHomeScreen: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    enterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = null,
    exitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = null,
    popEnterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = null,
    popExitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = null,
) {
    composable(
        route = addressRoute,
        enterTransition = enterTransition,
        exitTransition = exitTransition,
        popEnterTransition = popEnterTransition,
        popExitTransition = popExitTransition,
    ) { entry ->
        val viewModel = entry.sharedViewModel<RegisterViewModel>(navController = navController)
        AddressRoute(
            popBackStack = popBackStack,
            navigateToHomeScreen = navigateToHomeScreen,
            onShowSnackbar = onShowSnackbar,
            viewModel = viewModel
        )
    }
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(
    navController: NavHostController
): T {
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return hiltViewModel(parentEntry)
}