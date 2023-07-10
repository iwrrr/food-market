package com.hwaryun.designsystem.utils

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally

val enterTransition: EnterTransition by lazy {
    fadeIn(animationSpec = tween(200)) + slideInHorizontally(
        initialOffsetX = { 300 },
        animationSpec = tween(200)
    )
}

val exitTransition: ExitTransition by lazy {
    fadeOut(animationSpec = tween(200)) + slideOutHorizontally(
        targetOffsetX = { -300 },
        animationSpec = tween(200)
    )
}

val popEnterTransition: EnterTransition by lazy {
    fadeIn(animationSpec = tween(200)) + slideInHorizontally(
        initialOffsetX = { -300 },
        animationSpec = tween(200)
    )
}

val popExitTransition: ExitTransition by lazy {
    fadeOut(animationSpec = tween(200)) + slideOutHorizontally(
        targetOffsetX = { 300 },
        animationSpec = tween(200)
    )
}