package com.hwaryun.designsystem.components.atoms.utility

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.hwaryun.designsystem.ui.asphalt.LocalContentColor

object ButtonDefaults {

    private val ButtonHorizontalPadding = 12.dp
    private val ButtonVerticalPadding = 8.dp

    val ContentPadding =
        PaddingValues(
            start = ButtonHorizontalPadding,
            top = ButtonVerticalPadding,
            end = ButtonHorizontalPadding,
            bottom = ButtonVerticalPadding
        )

    private val ButtonWithIconHorizontalStartPadding = 16.dp

    val ButtonWithIconContentPadding =
        PaddingValues(
            start = ButtonWithIconHorizontalStartPadding,
            top = ButtonVerticalPadding,
            end = ButtonHorizontalPadding,
            bottom = ButtonVerticalPadding
        )

    private val TextButtonHorizontalPadding = 12.dp

    val TextButtonContentPadding =
        PaddingValues(
            start = TextButtonHorizontalPadding,
            top = ContentPadding.calculateTopPadding(),
            end = TextButtonHorizontalPadding,
            bottom = ContentPadding.calculateBottomPadding()
        )

    val MinWidth = 58.dp

    val MinHeight = 40.dp

    val IconSize = 18.dp

    val IconSpacing = 8.dp

    val shape: Shape @Composable get() = CircleShape

    val outlinedShape: Shape @Composable get() = CircleShape

    val textShape: Shape @Composable get() = CircleShape

    @Composable
    fun buttonColors(
        containerColor: Color,
        contentColor: Color,
        disabledContainerColor: Color,
        disabledContentColor: Color,
    ): ButtonColors = ButtonColors(
        containerColor = containerColor,
        contentColor = contentColor,
        disabledContainerColor = disabledContainerColor,
        disabledContentColor = disabledContentColor
    )

    @Composable
    fun outlinedButtonColors(
        containerColor: Color = Color.Transparent,
        contentColor: Color,
        disabledContainerColor: Color = Color.Transparent,
        disabledContentColor: Color,
    ): ButtonColors = ButtonColors(
        containerColor = containerColor,
        contentColor = contentColor,
        disabledContainerColor = disabledContainerColor,
        disabledContentColor = disabledContentColor
    )

    @Composable
    fun textButtonColors(
        containerColor: Color = Color.Transparent,
        contentColor: Color,
        disabledContainerColor: Color = Color.Transparent,
        disabledContentColor: Color,
    ): ButtonColors = ButtonColors(
        containerColor = containerColor,
        contentColor = contentColor,
        disabledContainerColor = disabledContainerColor,
        disabledContentColor = disabledContentColor
    )

    val outlinedButtonBorder: BorderStroke
        @Composable
        get() = BorderStroke(
            width = 1.dp,
            color = LocalContentColor.current,
        )
}