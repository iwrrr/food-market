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
import com.hwaryun.signup.SignUpRoute
import com.hwaryun.signup.SignUpViewModel

const val signUpGraphRoute = "sign_up_graph_route"
const val signUpRoute = "sign_up_route"
const val addressRoute = "address_route"

fun NavController.navigateToSignUpGraph(navOptions: NavOptions? = null) {
    this.navigate(signUpGraphRoute, navOptions)
}

fun NavController.navigateToAddress(navOptions: NavOptions? = null) {
    this.navigate(addressRoute, navOptions)
}

fun NavGraphBuilder.signUpGraph(
    navController: NavHostController,
    popBackStack: () -> Unit,
    navigateToAddressScreen: () -> Unit,
    nestedGraphs: NavGraphBuilder.() -> Unit
) {
    navigation(
        route = signUpGraphRoute,
        startDestination = signUpRoute
    ) {
        composable(route = signUpRoute) {entry ->
            val viewModel = entry.sharedViewModel<SignUpViewModel>(navController = navController)
            SignUpRoute(
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
        val viewModel = entry.sharedViewModel<SignUpViewModel>(navController = navController)
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