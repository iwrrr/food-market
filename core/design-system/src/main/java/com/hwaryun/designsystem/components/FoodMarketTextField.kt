package com.hwaryun.designsystem.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hwaryun.designsystem.R
import com.hwaryun.designsystem.ui.Black
import com.hwaryun.designsystem.ui.CoolGray
import com.hwaryun.designsystem.ui.CoolGray500
import com.hwaryun.designsystem.ui.FoodMarketTheme
import com.hwaryun.designsystem.ui.LightGreen
import com.hwaryun.designsystem.ui.SubBlack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodMarketTextField(
    showLabel: Boolean = false,
    textLabel: String = "",
    text: String,
    placeholder: String,
    isPasswordTextField: Boolean = false,
    isExpandedDropdown: Boolean = false,
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
    singleLine: Boolean = true,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {
    Column {
        if (showLabel) {
            Text(
                text = textLabel,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        if (isExpandedDropdown) {
            // We want to react on tap/press on TextField to show menu
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { onExpandedChange(it) }
            ) {
                CustomTextFieldWithError(
                    value = text,
                    onValueChange = onValueChange,
                    maxLines = 1,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = LightGreen,
                        cursorColor = Color.White
                    ),
                    placeholder = { Text(text = placeholder) },
                    isError = isError,
                    errorMsg = errorMsg,
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
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
                            text = { Text(text = selectionOption) },
                            onClick = {
                                onClickExpanded(selectionOption)
                            }
                        )
                    }
                }
            }
        } else {
            CustomTextFieldWithError(
                value = text,
                onValueChange = onValueChange,
                maxLines = 1,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = LightGreen,
                    cursorColor = Color.White
                ),
                placeholder = {
                    Text(
                        text = placeholder,
                        style = MaterialTheme.typography.bodySmall
                    )
                },
                isError = isError,
                errorMsg = errorMsg,
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = if (isPasswordTextField) PasswordVisualTransformation() else VisualTransformation.None,
                trailingIcon = trailingIcon,
                keyboardOptions = keyboardOptions,
                singleLine = singleLine,
                enabled = enabled,
                readOnly = readOnly,
                textStyle = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextFieldWithError(
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
    colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors(),
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
            TextFieldDefaults.TextFieldDecorationBox(
                enabled = enabled,
                value = value,
                singleLine = singleLine,
                label = label,
                placeholder = placeholder,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
                isError = isError,
                visualTransformation = visualTransformation,
                interactionSource = interactionSource,
                shape = shape,
                colors = colors,
                innerTextField = it,
                container = {
                    TextFieldDefaults.OutlinedBorderContainerBox(
                        enabled,
                        isError,
                        interactionSource,
                        colors,
                        shape
                    )
                }
            )
        }

        if (isError) {
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = errorMsg,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    label: String = "",
    showLabel: Boolean = false,
    required: Boolean = false,
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
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
        unfocusedBorderColor = CoolGray,
        focusedBorderColor = Black
    ),
    errorMsg: String = ""
) {
    Column(modifier = modifier) {
        if (showLabel) {
            val annotatedString = buildAnnotatedString {
                append(label)
                if (required) {
                    withStyle(style = SpanStyle(MaterialTheme.colorScheme.error)) {
                        append(" *")
                    }
                }
            }

            Text(
                text = annotatedString,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Medium,
                color = SubBlack,
            )
        }
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
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
                        Text(text = placeholder, style = textStyle, color = CoolGray500)
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
                        colors = colors,
                        interactionSource = interactionSource,
                    )
                },
            )
        }
        if (isError) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = errorMsg,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.labelSmall
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
        CustomTextField(
            value = "aaa",
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.bodyMedium,
            isError = false,
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
        //        var email = ""
        //        FoodMarketTextField(
        //            showLabel = true,
        //            textLabel = "Email",
        //            text = email,
        //            placeholder = "Enter an email address",
        //            isPasswordTextField = true,
        //            onValueChange = { email = it.trim() },
        //            isError = true,
        //            errorMsg = "*Enter valid email address",
        //            trailingIcon = {
        //                if (email.isNotBlank()) {
        //                    IconButton(onClick = { email = "" }) {
        //                        Icon(imageVector = Icons.Default.Clear, contentDescription = null)
        //                    }
        //                }
        //            }
        //        )
    }
}