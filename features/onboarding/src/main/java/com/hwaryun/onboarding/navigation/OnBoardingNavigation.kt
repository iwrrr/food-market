package com.hwaryun.onboarding.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.hwaryun.onboarding.OnBoardingRoute

const val onBoardingRoute = "on_boarding_route"

@ExperimentalAnimationApi
fun NavGraphBuilder.onBoardingScreen() {
    composable(
        route = onBoardingRoute,
    ) {
        OnBoardingRoute()
    }
}