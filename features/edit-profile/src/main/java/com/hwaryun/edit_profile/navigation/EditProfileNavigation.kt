package com.hwaryun.edit_profile.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation
import com.hwaryun.common.utils.sharedViewModel
import com.hwaryun.edit_profile.CameraRoute
import com.hwaryun.edit_profile.EditProfileRoute
import com.hwaryun.edit_profile.EditProfileViewModel

const val editProfileGraphRoute = "edit_profile_graph_route"
const val editProfileRoute = "edit_profile_route"
const val cameraRoute = "camera_route"

fun NavController.navigateToEditProfile(navOptions: NavOptions? = null) {
    this.navigate(editProfileGraphRoute, navOptions)
}

fun NavController.navigateToCameraScreen(navOptions: NavOptions? = null) {
    this.navigate(cameraRoute, navOptions)
}

@ExperimentalAnimationApi
fun NavGraphBuilder.editProfileGraph(
    navController: NavHostController,
    popBackStack: () -> Unit,
    navigateToCameraScreen: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    enterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = null,
    exitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = null,
    popEnterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = null,
    popExitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = null,
    nestedGraphs: NavGraphBuilder.() -> Unit
) {
    navigation(
        route = editProfileGraphRoute,
        startDestination = editProfileRoute
    ) {
        composable(
            route = editProfileRoute,
            enterTransition = enterTransition,
            exitTransition = exitTransition,
            popEnterTransition = popEnterTransition,
            popExitTransition = popExitTransition,
        ) { entry ->
            val viewModel =
                entry.sharedViewModel<EditProfileViewModel>(navController = navController)
            EditProfileRoute(
                popBackStack = popBackStack,
                onShowSnackbar = onShowSnackbar,
                navigateToCameraScreen = navigateToCameraScreen,
                viewModel = viewModel
            )
        }
        nestedGraphs()
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.cameraScreen(
    navController: NavHostController,
    popBackStack: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    enterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = null,
    exitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = null,
    popEnterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = null,
    popExitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = null,
) {
    composable(
        route = cameraRoute,
        enterTransition = enterTransition,
        exitTransition = exitTransition,
        popEnterTransition = popEnterTransition,
        popExitTransition = popExitTransition,
    ) { entry ->
        val viewModel = entry.sharedViewModel<EditProfileViewModel>(navController = navController)
        CameraRoute(
            popBackStack = popBackStack,
            onShowSnackbar = onShowSnackbar,
            viewModel = viewModel
        )
    }
}