package com.hwaryun.designsystem.components

import android.content.res.Configuration
import android.os.SystemClock
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hwaryun.designsystem.ui.Black500
import com.hwaryun.designsystem.ui.CoolGray1cCp500
import com.hwaryun.designsystem.ui.CoolGray500
import com.hwaryun.designsystem.ui.FoodMarketTheme
import com.hwaryun.designsystem.ui.GojekGreen500
import com.hwaryun.designsystem.ui.GojekGreen700
import com.hwaryun.designsystem.ui.PureWhite500
import com.hwaryun.designsystem.utils.ButtonType
import com.hwaryun.designsystem.utils.NoRippleTheme
import com.hwaryun.designsystem.utils.bounceClick

@Composable
fun FoodMarketButton(
    text: String,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(24.dp),
    type: ButtonType = ButtonType.Primary,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    clickDisablePeriod: Long = 1000L,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
        when (type) {
            ButtonType.Primary -> {
                val containerColor = if (isPressed) GojekGreen700 else GojekGreen500

                PrimaryButton(
                    text = text,
                    modifier = modifier
                        .height(44.dp)
                        .bounceClick(),
                    shape = shape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor,
                        PureWhite500,
                        CoolGray1cCp500,
                        PureWhite500
                    ),
                    enabled = enabled,
                    isLoading = isLoading,
                    clickDisablePeriod = clickDisablePeriod,
                    interactionSource = interactionSource,
                    onClick = onClick,
                )
            }

            ButtonType.Outline -> {
                val contentColor = if (isPressed) GojekGreen700 else GojekGreen500
                val enabledColor = if (enabled) GojekGreen500 else CoolGray500
                val borderColor = if (isPressed) contentColor else enabledColor

                OutlinedButton(
                    text = text,
                    modifier = modifier
                        .height(44.dp)
                        .bounceClick(),
                    shape = shape,
                    borderStroke = BorderStroke(1.dp, borderColor),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = contentColor,
                        disabledContentColor = CoolGray500
                    ),
                    enabled = enabled,
                    isLoading = isLoading,
                    clickDisablePeriod = clickDisablePeriod,
                    interactionSource = interactionSource,
                    onClick = onClick,
                )
            }

            ButtonType.Text -> {
                TextButton(
                    text = text,
                    modifier = modifier
                        .height(44.dp)
                        .bounceClick(),
                    shape = shape,
                    colors = ButtonDefaults.textButtonColors(contentColor = Black500),
                    enabled = enabled,
                    isLoading = isLoading,
                    clickDisablePeriod = clickDisablePeriod,
                    interactionSource = interactionSource,
                    onClick = onClick,
                )
            }
        }
    }
}

@Composable
private fun PrimaryButton(
    text: String,
    modifier: Modifier = Modifier,
    shape: Shape,
    colors: ButtonColors,
    enabled: Boolean,
    isLoading: Boolean,
    clickDisablePeriod: Long,
    interactionSource: MutableInteractionSource,
    onClick: () -> Unit
) {
    var lastClickTime by remember { mutableLongStateOf(0L) }

    Button(
        modifier = modifier,
        shape = shape,
        colors = colors,
        enabled = enabled,
        interactionSource = interactionSource,
        onClick = {
            if (SystemClock.elapsedRealtime() - lastClickTime < clickDisablePeriod) {
                return@Button
            } else {
                lastClickTime = SystemClock.elapsedRealtime()
                onClick()
            }
        },
    ) {
        ButtonContent(text = text, isLoading = isLoading)
    }
}

@Suppress("LABEL_NAME_CLASH")
@Composable
private fun OutlinedButton(
    text: String,
    modifier: Modifier = Modifier,
    shape: Shape,
    borderStroke: BorderStroke?,
    colors: ButtonColors,
    enabled: Boolean,
    isLoading: Boolean,
    clickDisablePeriod: Long,
    interactionSource: MutableInteractionSource,
    onClick: () -> Unit
) {
    var lastClickTime by remember { mutableLongStateOf(0L) }

    androidx.compose.material3.OutlinedButton(
        modifier = modifier,
        shape = shape,
        border = borderStroke,
        colors = colors,
        enabled = enabled,
        interactionSource = interactionSource,
        onClick = {
            if (SystemClock.elapsedRealtime() - lastClickTime < clickDisablePeriod) {
                return@OutlinedButton
            } else {
                lastClickTime = SystemClock.elapsedRealtime()
                onClick()
            }
        },
    ) {
        ButtonContent(text = text, isLoading = isLoading)
    }
}

@Suppress("LABEL_NAME_CLASH")
@Composable
private fun TextButton(
    text: String,
    modifier: Modifier = Modifier,
    shape: Shape,
    colors: ButtonColors,
    enabled: Boolean,
    isLoading: Boolean,
    clickDisablePeriod: Long,
    interactionSource: MutableInteractionSource,
    onClick: () -> Unit
) {
    var lastClickTime by remember { mutableLongStateOf(0L) }

    androidx.compose.material3.TextButton(
        modifier = modifier,
        shape = shape,
        colors = colors,
        enabled = enabled,
        interactionSource = interactionSource,
        onClick = {
            if (SystemClock.elapsedRealtime() - lastClickTime < clickDisablePeriod) {
                return@TextButton
            } else {
                lastClickTime = SystemClock.elapsedRealtime()
                onClick()
            }
        },
    ) {
        ButtonContent(text = text, isLoading = isLoading)
    }
}

@Composable
private fun ButtonContent(
    text: String,
    contentColor: Color = LocalContentColor.current,
    isLoading: Boolean,
) {
    if (isLoading) {
        CircularProgressIndicator(color = contentColor)
    } else {
        Text(
            text = text,
            color = contentColor,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true,
    backgroundColor = 0xFFFFFFFF
)
@Composable
private fun DefaultPreview() {
    FoodMarketTheme {
        FoodMarketButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Button",
            type = ButtonType.Text,
            isLoading = false,
            enabled = true,
            onClick = {}
        )
    }
}