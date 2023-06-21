package com.hwaryun.designsystem.ui.asphalt

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

@Composable
fun AsphaltTheme(
    colors: AsphaltColors = AsphaltTheme.colors,
    typography: AsphaltTypography = AsphaltTheme.typography,
    shapes: AsphaltShapes = AsphaltTheme.shapes,
    content: @Composable () -> Unit
) {

    CompositionLocalProvider(
        LocalColors provides colors,
        LocalTypography provides typography,
        LocalShapes provides shapes,
    ) {
        content()
    }
}

object AsphaltTheme {
    val colors: AsphaltColors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    val typography: AsphaltTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current

    val shapes: AsphaltShapes
        @Composable
        @ReadOnlyComposable
        get() = LocalShapes.current
}