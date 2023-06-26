package com.hwaryun.search.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.hwaryun.search.SearchRoute

const val searchGraphRoute = "search_graph_route"
const val searchRoute = "search_route"

fun NavController.navigateToSearchGraph(navOptions: NavOptions? = null) {
    this.navigate(searchGraphRoute, navOptions)
}

fun NavGraphBuilder.searchGraph(
    navigateToHome: () -> Unit,
    onFoodClick: (Int) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    nestedGraphs: NavGraphBuilder.() -> Unit,
) {
    navigation(
        route = searchGraphRoute,
        startDestination = searchRoute
    ) {
        composable(route = searchRoute) {
            SearchRoute(
                navigateToHome = navigateToHome,
                onFoodClick = onFoodClick,
                onShowSnackbar = onShowSnackbar,
            )
        }
        nestedGraphs()
    }
}