package com.hwaryun.food_detail.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.composable
import com.hwaryun.food_detail.FoodDetailRoute

const val FOOD_ID = "foodId"

const val foodDetailsRoute = "food_details_route/{$FOOD_ID}"

fun NavController.navigateToFoodDetails(foodId: Int, navOptions: NavOptions? = null) {
    this.navigate("food_details_route/$foodId", navOptions)
}

@ExperimentalAnimationApi
fun NavGraphBuilder.foodDetailsScreen(
    popBackStack: () -> Unit,
    navigateToCart: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    enterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = null,
    exitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = null,
    popEnterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = null,
    popExitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = null,
) {
    composable(
        route = foodDetailsRoute,
        arguments = listOf(
            navArgument(FOOD_ID) { type = NavType.IntType }
        ),
        enterTransition = enterTransition,
        exitTransition = exitTransition,
        popEnterTransition = popEnterTransition,
        popExitTransition = popExitTransition,
    ) {
        FoodDetailRoute(
            popBackStack = popBackStack,
            navigateToCart = navigateToCart,
            onShowSnackbar = onShowSnackbar
        )
    }
}