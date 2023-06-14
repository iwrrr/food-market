package com.hwaryun.profile.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.hwaryun.profile.ProfileRoute

const val profileGraphRoute = "profile_graph_route"
const val profileRoute = "profile_route"

fun NavController.navigateToProfileGraph(navOptions: NavOptions? = null) {
    this.navigate(profileGraphRoute, navOptions)
}

fun NavGraphBuilder.profileGraph(
    nestedGraphs: NavGraphBuilder.() -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    navigation(
        route = profileGraphRoute,
        startDestination = profileRoute
    ) {
        composable(route = profileRoute) {
            ProfileRoute()
        }
        nestedGraphs()
    }
}