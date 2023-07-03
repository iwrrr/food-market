package com.hwaryun.designsystem.utils

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally

val enterTransition: EnterTransition by lazy {
    slideInHorizontally(
        initialOffsetX = { 300 },
        animationSpec = tween(200)
    ) + fadeIn(animationSpec = tween(200))
}

val exitTransition: ExitTransition by lazy {
    slideOutHorizontally(
        targetOffsetX = { -300 },
        animationSpec = tween(200)
    ) + fadeOut(animationSpec = tween(200))
}

val popEnterTransition: EnterTransition by lazy {
    slideInHorizontally(
        initialOffsetX = { -300 },
        animationSpec = tween(200)
    ) + fadeIn(animationSpec = tween(200))
}

val popExitTransition: ExitTransition by lazy {
    slideOutHorizontally(
        targetOffsetX = { 300 },
        animationSpec = tween(200)
    ) + fadeOut(animationSpec = tween(200))
}