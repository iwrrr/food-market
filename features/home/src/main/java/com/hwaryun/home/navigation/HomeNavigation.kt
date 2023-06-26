package com.hwaryun.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.hwaryun.home.HomeRoute

const val homeGraphRoute = "home_graph_route"
const val homeRoute = "home_route"

const val FOOD_ID = "foodId"
const val foodDetailsRoute = "food_details_route/{$FOOD_ID}"

fun NavController.navigateToHomeGraph(navOptions: NavOptions? = null) {
    this.navigate(homeGraphRoute, navOptions)
}

fun NavGraphBuilder.homeGraph(
    onCartClick: () -> Unit,
    onFoodClick: (Int) -> Unit,
    nestedGraphs: NavGraphBuilder.() -> Unit,
) {
    navigation(
        route = homeGraphRoute,
        startDestination = homeRoute
    ) {
        composable(
            route = homeRoute
        ) {
            HomeRoute(
                onCartClick = onCartClick,
                onFoodClick = onFoodClick
            )
        }
        nestedGraphs()
    }
}