package com.hwaryun.designsystem.components.atoms

import android.content.res.Configuration
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hwaryun.designsystem.R
import com.hwaryun.designsystem.ui.FoodMarketTheme
import com.hwaryun.designsystem.ui.asphalt.AsphaltTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AsphaltInput(
    value: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = AsphaltTheme.typography.titleModerateBold,
    placeholder: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        cursorColor = AsphaltTheme.colors.gojek_green_500,
        errorCursorColor = AsphaltTheme.colors.retail_red_500,
        focusedBorderColor = AsphaltTheme.colors.sub_black_500,
        unfocusedBorderColor = AsphaltTheme.colors.cool_gray_500,
        disabledBorderColor = AsphaltTheme.colors.cool_gray_1cCp_500,
        errorBorderColor = AsphaltTheme.colors.retail_red_500,
        focusedLeadingIconColor = AsphaltTheme.colors.sub_black_500,
        unfocusedLeadingIconColor = AsphaltTheme.colors.cool_gray_500,
        disabledLeadingIconColor = AsphaltTheme.colors.cool_gray_1cCp_500,
        errorLeadingIconColor = AsphaltTheme.colors.sub_black_500,
        focusedTrailingIconColor = AsphaltTheme.colors.sub_black_500,
        unfocusedTrailingIconColor = AsphaltTheme.colors.cool_gray_500,
        disabledTrailingIconColor = AsphaltTheme.colors.cool_gray_1cCp_500,
        errorTrailingIconColor = AsphaltTheme.colors.sub_black_500,
    ),
) {
    val textSelectionColors = TextSelectionColors(
        handleColor = AsphaltTheme.colors.gojek_green_500,
        backgroundColor = AsphaltTheme.colors.gojek_green_50
    )

    CompositionLocalProvider(
        LocalTextSelectionColors provides textSelectionColors
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier
                .height(36.dp),
            enabled = enabled,
            readOnly = readOnly,
            textStyle = textStyle,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            singleLine = singleLine,
            maxLines = maxLines,
            visualTransformation = visualTransformation,
            interactionSource = interactionSource,
            cursorBrush = SolidColor(AsphaltTheme.colors.gojek_green_500),
        ) { innerTextField ->
            TextFieldDefaults.OutlinedTextFieldDecorationBox(
                value = value,
                innerTextField = innerTextField,
                enabled = enabled,
                singleLine = singleLine,
                visualTransformation = visualTransformation,
                interactionSource = interactionSource,
                isError = isError,
                placeholder = {
                    placeholder?.let {
                        AsphaltText(
                            text = placeholder,
                            color = AsphaltTheme.colors.cool_gray_500,
                            style = textStyle
                        )
                    }
                },
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
                colors = colors,
                contentPadding = TextFieldDefaults.textFieldWithoutLabelPadding(
                    top = 10.dp,
                    bottom = 0.dp,
                    start = 0.dp,
                    end = 0.dp
                ),
                container = {
                    TextFieldDefaults.FilledContainerBox(
                        enabled = enabled,
                        isError = isError,
                        interactionSource = interactionSource,
                        colors = colors,
                    )
                },
            )
        }
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true,
    backgroundColor = 0xFFFFFFFF
)
@Composable
fun FoodMarketTextFieldPreview() {
    FoodMarketTheme {
        AsphaltInput(
            value = "aaa",
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            isError = true,
            trailingIcon = {
                IconButton(
                    modifier = Modifier.size(20.dp),
                    onClick = {}
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_close_circle_bold),
                        contentDescription = "Password Toggle",
                    )
                }
            }
        )
    }
}