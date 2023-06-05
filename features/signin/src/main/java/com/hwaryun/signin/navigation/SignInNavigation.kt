package com.hwaryun.signin.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.hwaryun.signin.SignInRoute

const val signInGraphRoute = "sign_in_graph_route"
const val signInRoute = "sign_in_route"

fun NavController.navigateToSignInGraph(navOptions: NavOptions? = null) {
    this.navigate(signInGraphRoute, navOptions)
}

fun NavGraphBuilder.signInGraph(
    navigateToSignUpScreen: () -> Unit,
    navigateToHomeScreen: () -> Unit,
) {
    navigation(
        route = signInGraphRoute,
        startDestination = signInRoute
    ) {
        composable(route = signInRoute) {
            SignInRoute(
                navigateToSignUpScreen = navigateToSignUpScreen,
                navigateToHomeScreen = navigateToHomeScreen
            )
        }
    }
}