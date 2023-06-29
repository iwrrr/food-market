package com.hwaryun.onboarding.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.hwaryun.onboarding.OnBoardingRoute

const val onBoardingRoute = "on_boarding_route"

fun NavController.navigateToHomeGraph(navOptions: NavOptions? = null) {
    this.navigate(onBoardingRoute, navOptions)
}

fun NavGraphBuilder.onBoardingScreen() {
    composable(
        route = onBoardingRoute
    ) {
        OnBoardingRoute()
    }
}