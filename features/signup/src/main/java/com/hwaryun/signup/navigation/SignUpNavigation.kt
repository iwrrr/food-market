package com.hwaryun.signup.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.hwaryun.signup.AddressRoute
import com.hwaryun.signup.SignUpRoute

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
    popBackStack: () -> Unit,
    navigateToAddressScreen: () -> Unit,
    nestedGraphs: NavGraphBuilder.() -> Unit,
) {
    navigation(
        route = signUpGraphRoute,
        startDestination = signUpRoute
    ) {
        composable(route = signUpRoute) {
            SignUpRoute(
                popBackStack = popBackStack,
                navigateToAddressScreen = navigateToAddressScreen
            )
        }
        nestedGraphs()
    }
}

fun NavGraphBuilder.addressScreen(
    popBackStack: () -> Unit,
    navigateToHomeScreen: () -> Unit,
) {
    composable(route = addressRoute) {
        AddressRoute(
            popBackStack = popBackStack,
            navigateToHomeScreen = navigateToHomeScreen
        )
    }
}