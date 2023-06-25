package com.hwaryun.signup.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
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

fun NavGraphBuilder.registerGraph(
    navController: NavHostController,
    popBackStack: () -> Unit,
    navigateToAddressScreen: () -> Unit,
    nestedGraphs: NavGraphBuilder.() -> Unit
) {
    navigation(
        route = registerGraphRoute,
        startDestination = registerRoute
    ) {
        composable(route = registerRoute) { entry ->
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

fun NavGraphBuilder.addressScreen(
    navController: NavHostController,
    popBackStack: () -> Unit,
    navigateToHomeScreen: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    composable(route = addressRoute) { entry ->
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