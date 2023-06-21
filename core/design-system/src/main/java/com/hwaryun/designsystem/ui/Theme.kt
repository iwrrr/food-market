package com.hwaryun.designsystem.ui

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.hwaryun.designsystem.ui.asphalt.AsphaltShapes
import com.hwaryun.designsystem.ui.asphalt.AsphaltTheme
import com.hwaryun.designsystem.ui.asphalt.AsphaltTypography
import com.hwaryun.designsystem.ui.asphalt.darkAsphaltColors
import com.hwaryun.designsystem.ui.asphalt.lightAsphaltColors

private val DarkColorPalette = darkAsphaltColors()

private val LightColorPalette = lightAsphaltColors()

@Composable
fun FoodMarketTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colors.pure_white_500.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
        }
    }

    AsphaltTheme(
        colors = colors,
        typography = AsphaltTypography(),
        shapes = AsphaltShapes(),
        content = content
    )
}