package com.hwaryun.food_detail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.hwaryun.food_detail.FoodDetailRoute

const val FOOD_ID = "foodId"

const val foodDetailsRoute = "food_details_route/{$FOOD_ID}"

fun NavController.navigateToFoodDetails(foodId: Int, navOptions: NavOptions? = null) {
    this.navigate("food_details_route/$foodId", navOptions)
}

fun NavGraphBuilder.foodDetailsScreen(
    popBackStack: () -> Unit,
    navigateToCart: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    composable(
        route = foodDetailsRoute,
        arguments = listOf(
            navArgument(FOOD_ID) { type = NavType.IntType }
        )
    ) {
        FoodDetailRoute(
            popBackStack = popBackStack,
            navigateToCart = navigateToCart,
            onShowSnackbar = onShowSnackbar
        )
    }
}