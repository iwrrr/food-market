//package com.hwaryun.common.ext
//
//import androidx.compose.animation.AnimatedContentScope
//import androidx.compose.animation.ExperimentalAnimationApi
//import androidx.compose.animation.core.tween
//import androidx.compose.runtime.Composable
//import androidx.navigation.NavGraphBuilder
//
//@OptIn(ExperimentalAnimationApi::class)
//fun NavGraphBuilder.composable(
//    route: String,
//    content: @Composable () -> Unit,
//) {
//
//    val enterTransition =
//        slideIntoContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(400))
//
//    val exitTransition =
//        slideOutOfContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(400))
//
//    val popExitTransition =
//        slideOutOfContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(300))
//
//    val popEnterTransition =
//        slideIntoContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(350))
//
//    composable(
//        route = route,
//        enterTransition = { _, _ ->
//            enterTransition
//        },
//        popEnterTransition = { _, _ ->
//            popEnterTransition
//        },
//        popExitTransition = { _, _ ->
//            popExitTransition
//        },
//        exitTransition = { _, _ ->
//            exitTransition
//        }
//    ) {
//        content()
//    }
//}