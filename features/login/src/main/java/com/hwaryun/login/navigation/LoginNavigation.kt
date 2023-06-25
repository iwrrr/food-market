package com.hwaryun.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.hwaryun.login.LoginRoute

const val loginGraphRoute = "login_graph_route"
const val loginRoute = "login_route"

fun NavController.navigateToLoginGraph(navOptions: NavOptions? = null) {
    this.navigate(loginGraphRoute, navOptions)
}

fun NavGraphBuilder.loginGraph(
    navigateToSignUpScreen: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    navigation(
        route = loginGraphRoute,
        startDestination = loginRoute
    ) {
        composable(route = loginRoute) {
            LoginRoute(
                navigateToSignUpScreen = navigateToSignUpScreen,
                onShowSnackbar = onShowSnackbar,
            )
        }
    }
}