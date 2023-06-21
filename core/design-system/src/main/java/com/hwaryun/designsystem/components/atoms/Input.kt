package com.hwaryun.designsystem.components.atoms

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hwaryun.designsystem.ui.FoodMarketTheme
import com.hwaryun.designsystem.ui.asphalt.AsphaltTheme
import com.hwaryun.designsystem.ui.asphalt.LocalContentColor

sealed interface InputType {
    object Default : InputType
    object Success : InputType
    object Error : InputType
}

private val MinHeight = 40.dp
private val MinWidth = 77.dp

@Composable
fun Input(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(8.dp),
    textStyle: TextStyle = AsphaltTheme.typography.bodyModerate.copy(fontWeight = FontWeight.Normal),
    inputType: InputType = InputType.Default,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {

    val textColor = textStyle.color

    val mergedTextStyle = textStyle.merge(TextStyle(color = textColor))

    val cursorColor = when (inputType) {
        InputType.Default -> AsphaltTheme.colors.black_500
        InputType.Error -> AsphaltTheme.colors.retail_red_500
        InputType.Success -> AsphaltTheme.colors.gojek_green_500
    }

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .background(
                color = AsphaltTheme.colors.pure_white_500,
                shape = shape,
            )
            .defaultMinSize(
                minWidth = MinWidth,
                minHeight = MinHeight,
            ),
        enabled = enabled,
        textStyle = mergedTextStyle,
        cursorBrush = SolidColor(cursorColor),
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = true,
        maxLines = 1,
        decorationBox = @Composable { innerTextField ->
            InputDecoration(
                value = value,
                inputType = inputType,
                innerTextField = innerTextField,
                shape = shape,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
                singleLine = false,
                interactionSource = interactionSource,
            )
        }

    )
}

@Composable
private fun InputDecoration(
    value: String,
    inputType: InputType,
    interactionSource: MutableInteractionSource,
    shape: Shape,
    innerTextField: @Composable () -> Unit,
    leadingIcon: @Composable() (() -> Unit)? = null,
    trailingIcon: @Composable() (() -> Unit)? = null,
    singleLine: Boolean = false,
) {
    val borderSize = when (inputType) {
        InputType.Default -> 1.dp
        InputType.Error -> 2.dp
        InputType.Success -> 2.dp
    }

    val iconColor = when (inputType) {
        InputType.Default -> AsphaltTheme.colors.black_500
        InputType.Error -> AsphaltTheme.colors.retail_red_500
        InputType.Success -> AsphaltTheme.colors.gojek_green_500
    }

    val borderColor = when (inputType) {
        InputType.Default -> AsphaltTheme.colors.black_500
        InputType.Error -> AsphaltTheme.colors.retail_red_500
        InputType.Success -> AsphaltTheme.colors.gojek_green_500
    }

    Box(
        modifier = Modifier.border(
            border = BorderStroke(borderSize, borderColor),
            shape = shape,
        )
    ) {
        Row(
            modifier = Modifier.padding(top = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            leadingIcon?.let {
                leadingIcon()
                Spacer(modifier = Modifier.width(8.dp))
            }
            innerTextField()
            trailingIcon?.let {
                Spacer(modifier = Modifier.width(8.dp))
                CompositionLocalProvider(
                    LocalContentColor provides iconColor
                ) {
                    trailingIcon()
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun DefaultPreview() {
    FoodMarketTheme {
        Input(value = "Test", onValueChange = {})
    }
}