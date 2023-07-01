package com.hwaryun.designsystem.components.molecules

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hwaryun.designsystem.components.atoms.AsphaltText
import com.hwaryun.designsystem.components.atoms.LocalTextStyle
import com.hwaryun.designsystem.ui.FoodMarketTheme
import com.hwaryun.designsystem.ui.LightGreen
import com.hwaryun.designsystem.ui.asphalt.AsphaltTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AsphaltDropdown(
    text: String,
    modifier: Modifier = Modifier,
    showLabel: Boolean = false,
    label: String = "",
    placeholder: String,
    expanded: Boolean = false,
    onExpandedChange: (Boolean) -> Unit = {},
    onDismissRequest: () -> Unit = {},
    onClickExpanded: (String) -> Unit = {},
    options: List<String> = emptyList(),
    isError: Boolean = false,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    onValueChange: (String) -> Unit,
    errorMsg: String = "",
) {
    Column(
        modifier = modifier
    ) {
        if (showLabel) {
            val annotatedString = buildAnnotatedString {
                append(label)
                withStyle(style = SpanStyle(AsphaltTheme.colors.retail_red_500)) {
                    append(" *")
                }
            }

            AsphaltText(
                text = annotatedString,
                modifier = Modifier.fillMaxWidth(),
                color = AsphaltTheme.colors.cool_gray_500,
                style = AsphaltTheme.typography.captionModerateDemi,
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        // We want to react on tap/press on TextField to show menu
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { onExpandedChange(it) }
        ) {
            CustomTextFieldWithError(
                value = text,
                onValueChange = onValueChange,
                maxLines = 1,
                colors = OutlinedTextFieldDefaults.colors(
                    cursorColor = Color.White,
                    focusedBorderColor = LightGreen,
                ),
                placeholder = {
                    AsphaltText(
                        text = placeholder,
                        color = AsphaltTheme.colors.cool_gray_500,
                        style = AsphaltTheme.typography.titleSmallDemi,
                    )
                },
                isError = isError,
                errorMsg = errorMsg,
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
                    .height(44.dp)
                    .padding(0.dp),
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
                    )
                },
                enabled = enabled,
                readOnly = readOnly
            )
            ExposedDropdownMenu(
                modifier = Modifier.background(MaterialTheme.colorScheme.surface),
                expanded = expanded,
                onDismissRequest = { onDismissRequest() }
            ) {
                options.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = {
                            AsphaltText(
                                text = selectionOption,
                                style = AsphaltTheme.typography.captionModerateDemi
                            )
                        },
                        onClick = {
                            onClickExpanded(selectionOption)
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CustomTextFieldWithError(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = MaterialTheme.shapes.small,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(
    ),
    errorMsg: String = ""
) {
    Column(
        modifier = Modifier
            .padding(
                bottom = if (isError) {
                    0.dp
                } else {
                    10.dp
                }
            )
    ) {

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier,
            enabled = enabled,
            readOnly = readOnly,
            textStyle = textStyle,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            singleLine = singleLine,
            maxLines = maxLines,
            visualTransformation = visualTransformation
        ) {
            TextFieldDefaults.DecorationBox(
                value = value,
                innerTextField = it,
                enabled = enabled,
                singleLine = singleLine,
                visualTransformation = visualTransformation,
                interactionSource = interactionSource,
                isError = isError,
                label = label,
                placeholder = placeholder,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
                shape = shape,
                colors = colors,
                contentPadding = OutlinedTextFieldDefaults.contentPadding(
                    top = 12.dp,
                    bottom = 0.dp,
                ),
                container = {
                    OutlinedTextFieldDefaults.ContainerBox(
                        enabled = enabled,
                        isError = isError,
                        interactionSource = interactionSource,
                        colors = colors,
                        shape = shape,
                        focusedBorderThickness = OutlinedTextFieldDefaults.FocusedBorderThickness,
                        unfocusedBorderThickness = OutlinedTextFieldDefaults.UnfocusedBorderThickness,
                    )
                },
            )
        }

        if (isError) {
            Spacer(modifier = Modifier.height(2.dp))
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
    }
}