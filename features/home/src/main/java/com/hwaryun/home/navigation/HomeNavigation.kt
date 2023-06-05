package com.hwaryun.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.hwaryun.home.FoodDetailRoute
import com.hwaryun.home.HomeRoute

const val homeGraphRoute = "home_graph_route"
const val homeRoute = "home_route"
const val foodDetailsRoute = "food_details_route"

fun NavController.navigateToHomeGraph(navOptions: NavOptions? = null) {
    this.navigate(homeGraphRoute, navOptions)
}

fun NavController.navigateToFoodDetails(navOptions: NavOptions? = null) {
    this.navigate(foodDetailsRoute, navOptions)
}

fun NavGraphBuilder.homeGraph(
    onFoodClick: () -> Unit,
    nestedGraphs: NavGraphBuilder.() -> Unit,
) {
    navigation(
        route = homeGraphRoute,
        startDestination = homeRoute
    ) {
        composable(route = homeRoute) {
            HomeRoute(onFoodClick)
        }
        nestedGraphs()
    }
}

fun NavGraphBuilder.foodDetailsScreen(
    onOrderClick: () -> Unit,
) {
    composable(route = foodDetailsRoute) {
        FoodDetailRoute(
            onOrderClick = onOrderClick
        )
    }
}