package com.hwaryun.signup

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hwaryun.designsystem.R
import com.hwaryun.designsystem.components.AsphaltAppBar
import com.hwaryun.designsystem.components.FoodMarketCircleImage
import com.hwaryun.designsystem.components.atoms.AsphaltButton
import com.hwaryun.designsystem.components.atoms.AsphaltText
import com.hwaryun.designsystem.components.molecules.AsphaltInputGroup
import com.hwaryun.designsystem.ui.FoodMarketTheme
import com.hwaryun.signup.state.SignUpState

@Composable
internal fun SignUpRoute(
    popBackStack: () -> Unit,
    navigateToAddressScreen: () -> Unit,
    viewModel: SignUpViewModel
) {
    val signUpState by viewModel.signUpState.collectAsStateWithLifecycle()

    SignUpScreen(
        signUpState = signUpState,
        popBackStack = popBackStack,
        navigateToAddressScreen = navigateToAddressScreen,
        updateNameState = viewModel::updateNameState,
        updateEmailState = viewModel::updateEmailState,
        updatePasswordState = viewModel::updatePasswordState,
        updateIsPasswordVisible = viewModel::updateIsPasswordVisible,
        validateFirstStep = viewModel::validateFirstStep,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    signUpState: SignUpState,
    popBackStack: () -> Unit,
    navigateToAddressScreen: () -> Unit,
    updateNameState: (String) -> Unit,
    updateEmailState: (String) -> Unit,
    updatePasswordState: (String) -> Unit,
    updateIsPasswordVisible: (Boolean) -> Unit,
    validateFirstStep: (String, String, String) -> Unit,
) {
    var isFirstStepCompleted by rememberSaveable { mutableStateOf(true) }
    var shouldShowTrailingIcon by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    if (signUpState.firstStep != null && isFirstStepCompleted) {
        LaunchedEffect(Unit) {
            navigateToAddressScreen()
            isFirstStepCompleted = false
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AsphaltAppBar(
                title = "Sign Up",
                subtitle = "Register and eat",
                showNavigateBack = true,
                onNavigateBack = { popBackStack() }
            )
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(top = innerPadding.calculateTopPadding() + 24.dp)
                    .fillMaxSize(),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surface)
                        .verticalScroll(rememberScrollState())
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    FoodMarketCircleImage(width = 110.dp, height = 110.dp, borderEnabled = true)
                    Spacer(modifier = Modifier.height(24.dp))
                    AsphaltInputGroup(
                        value = signUpState.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .onFocusChanged { shouldShowTrailingIcon = it.isFocused },
                        onValueChange = { updateNameState(it) },
                        showLabel = true,
                        required = true,
                        label = "Full Name",
                        placeholder = "Type your full name",
                        singleLine = true,
                        isError = signUpState.isNameError,
                        errorMsg = if (signUpState.isNameError) stringResource(id = signUpState.errorNameMsg) else "",
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = {
                                focusManager.moveFocus(FocusDirection.Down)
                            }
                        ),
                        trailingIcon = {
                            if (signUpState.name.isNotEmpty() && shouldShowTrailingIcon) {
                                IconButton(
                                    modifier = Modifier.size(20.dp),
                                    onClick = { updateEmailState("") }
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_close_circle_bold),
                                        contentDescription = "Clear Text"
                                    )
                                }
                            }
                        },
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    AsphaltInputGroup(
                        value = signUpState.email,
                        modifier = Modifier
                            .fillMaxWidth()
                            .onFocusChanged { shouldShowTrailingIcon = it.isFocused },
                        onValueChange = { updateEmailState(it) },
                        showLabel = true,
                        required = true,
                        label = "Email Address",
                        placeholder = "Type your email address",
                        singleLine = true,
                        isError = signUpState.isEmailError,
                        errorMsg = if (signUpState.isEmailError) stringResource(id = signUpState.errorEmailMsg) else "",
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = {
                                focusManager.moveFocus(FocusDirection.Down)
                            }
                        ),
                        trailingIcon = {
                            if (signUpState.email.isNotEmpty() && shouldShowTrailingIcon) {
                                IconButton(
                                    modifier = Modifier.size(20.dp),
                                    onClick = { updateEmailState("") }
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_close_circle_bold),
                                        contentDescription = "Clear Text"
                                    )
                                }
                            }
                        },
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    AsphaltInputGroup(
                        value = signUpState.password,
                        modifier = Modifier.fillMaxWidth(),
                        onValueChange = { updatePasswordState(it) },
                        showLabel = true,
                        required = true,
                        label = "Password",
                        placeholder = "Type your password",
                        singleLine = true,
                        visualTransformation = if (!signUpState.isPasswordVisible) PasswordVisualTransformation() else VisualTransformation.None,
                        isError = signUpState.isPasswordError,
                        errorMsg = if (signUpState.isPasswordError) stringResource(id = signUpState.errorPasswordMsg) else "",
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        trailingIcon = {
                            IconButton(
                                modifier = Modifier.size(20.dp),
                                onClick = { updateIsPasswordVisible(!signUpState.isPasswordVisible) }
                            ) {
                                val icon = if (signUpState.isPasswordVisible) {
                                    painterResource(id = R.drawable.ic_eye_slash_bold)
                                } else {
                                    painterResource(id = R.drawable.ic_eye_bold)
                                }

                                Icon(
                                    painter = icon,
                                    contentDescription = "Password Toggle"
                                )
                            }
                        },
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    AsphaltButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            validateFirstStep(
                                signUpState.name,
                                signUpState.email,
                                signUpState.password
                            )
                            isFirstStepCompleted = true
                        }
                    ) {
                        AsphaltText(text = "Continue")
                    }
                }
            }
        }
    )
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DefaultPreview() {
    FoodMarketTheme {
        SignUpScreen(
            popBackStack = {},
            navigateToAddressScreen = {},
            signUpState = SignUpState(),
            updateNameState = {},
            updateEmailState = {},
            updatePasswordState = {},
            updateIsPasswordVisible = {},
            validateFirstStep = { _, _, _ -> },
        )
    }
}