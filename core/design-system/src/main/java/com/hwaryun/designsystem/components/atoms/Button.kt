package com.hwaryun.designsystem.components.atoms

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hwaryun.designsystem.components.atoms.basic.Surface
import com.hwaryun.designsystem.components.atoms.utility.ButtonDefaults
import com.hwaryun.designsystem.ui.FoodMarketTheme
import com.hwaryun.designsystem.ui.asphalt.AsphaltTheme
import com.hwaryun.designsystem.ui.asphalt.LocalContentColor
import com.hwaryun.designsystem.ui.asphalt.contentColorFor
import com.hwaryun.designsystem.ui.asphalt.pressedColorFor
import com.hwaryun.designsystem.utils.bounceClick

sealed interface ButtonType {
    object Primary : ButtonType
    object Secondary : ButtonType
    object Outline : ButtonType
    object Text : ButtonType
}

@Composable
fun Button(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String = "",
    enabled: Boolean = true, // not handled for simplicity
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    shape: Shape = AsphaltTheme.shapes.medium, // configure type
    type: ButtonType = ButtonType.Primary, // configure shape
    color: Color = AsphaltTheme.colors.gojek_green_500,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val colors = when (type) {
        ButtonType.Primary -> {
            ButtonDefaults.buttonColors(
                containerColor = if (isPressed) pressedColorFor(color) else color,
                contentColor = AsphaltTheme.colors.pure_white_500,
                disabledContainerColor = AsphaltTheme.colors.cool_gray_1cCp_500,
                disabledContentColor = AsphaltTheme.colors.pure_white_500
            )
        }

        ButtonType.Secondary -> {
            ButtonDefaults.buttonColors(
                containerColor = if (isPressed) pressedColorFor(color) else color,
                contentColor = contentColorFor(color),
                disabledContainerColor = AsphaltTheme.colors.cool_gray_1cCp_100,
                disabledContentColor = AsphaltTheme.colors.cool_gray_500
            )
        }

        ButtonType.Outline -> {
            ButtonDefaults.outlinedButtonColors(
                containerColor = AsphaltTheme.colors.pure_white_500,
                contentColor = if (isPressed) pressedColorFor(color) else color,
                disabledContainerColor = AsphaltTheme.colors.pure_white_500,
                disabledContentColor = AsphaltTheme.colors.cool_gray_500
            )
        }

        ButtonType.Text -> {
            ButtonDefaults.textButtonColors(
                contentColor = color,
                disabledContentColor = color
            )
        }
    }

    val containerColor = colors.containerColor(enabled).value
    val contentColor = colors.contentColor(enabled).value

    val border: BorderStroke? = when (type) {
        ButtonType.Outline -> BorderStroke(1.dp, contentColor)
        else -> null
    }

    Surface(
        onClick = onClick,
        modifier = modifier.bounceClick(),
        enabled = enabled,
        shape = shape,
        color = containerColor,
        contentColor = contentColor,
        border = border,
        interactionSource = interactionSource,
    ) {
        ProvideTextStyle(value = AsphaltTheme.typography.bodyModerate) {
            Row(
                modifier = Modifier.padding(
                    horizontal = 16.dp,
                    vertical = 12.dp,
                ),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                leadingIcon?.let {
                    leadingIcon()
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Content(text = text, modifier = Modifier.fillMaxWidth())
                trailingIcon?.let {
                    Spacer(modifier = Modifier.width(8.dp))
                    trailingIcon()
                }
            }
        }
    }
}

@Composable
private fun Content(
    text: String,
    modifier: Modifier = Modifier,
    contentColor: Color = LocalContentColor.current,
    isLoading: Boolean = false,
) {
    if (isLoading) {
        CircularProgressIndicator(color = contentColor)
    } else {
        Text(
            text = text,
            modifier = modifier,
            color = contentColor,
            style = AsphaltTheme.typography.titleTinyBold,
        )
    }
}

@Preview
@Composable
private fun DefaultPreview() {
    FoodMarketTheme {
        Button(
            text = "Test",
            enabled = true,
            onClick = {}
        )
    }
}