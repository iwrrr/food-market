package com.hwaryun.designsystem.components.molecules

import android.content.res.Configuration
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hwaryun.designsystem.R
import com.hwaryun.designsystem.components.atoms.AsphaltInput
import com.hwaryun.designsystem.components.atoms.AsphaltText
import com.hwaryun.designsystem.ui.FoodMarketTheme
import com.hwaryun.designsystem.ui.asphalt.AsphaltTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AsphaltInputGroup(
    value: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    label: String = "",
    showLabel: Boolean = false,
    required: Boolean = false,
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
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(
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
    errorMsg: String = ""
) {
    Column(modifier = modifier) {
        if (showLabel) {
            val annotatedString = buildAnnotatedString {
                append(label)
                if (required) {
                    withStyle(style = SpanStyle(AsphaltTheme.colors.retail_red_500)) {
                        append(" *")
                    }
                }
            }

            AsphaltText(
                text = annotatedString,
                modifier = Modifier.fillMaxWidth(),
                color = AsphaltTheme.colors.cool_gray_500,
                style = AsphaltTheme.typography.captionModerateDemi,
            )
        }
        AsphaltInput(
            modifier = modifier,
            value = value,
            onValueChange = onValueChange,
            enabled = enabled,
            readOnly = readOnly,
            textStyle = textStyle,
            placeholder = placeholder,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            isError = isError,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            singleLine = singleLine,
            maxLines = maxLines,
            interactionSource = interactionSource,
            colors = colors,
        )
        if (isError) {
            Spacer(modifier = Modifier.height(4.dp))
            AsphaltText(
                text = errorMsg,
                modifier = Modifier.fillMaxWidth(),
                color = AsphaltTheme.colors.retail_red_500,
                style = AsphaltTheme.typography.captionSmallBook
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
        AsphaltInputGroup(
            value = "aaa",
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            isError = true,
            errorMsg = "Cuma bisa antara",
            label = "Nama",
            showLabel = true,
            required = true,
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