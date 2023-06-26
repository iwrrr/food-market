package com.hwaryun.login

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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hwaryun.designsystem.R
import com.hwaryun.designsystem.components.AsphaltAppBar
import com.hwaryun.designsystem.components.atoms.AsphaltButton
import com.hwaryun.designsystem.components.atoms.AsphaltText
import com.hwaryun.designsystem.components.atoms.ButtonType
import com.hwaryun.designsystem.components.molecules.AsphaltInputGroup
import com.hwaryun.designsystem.ui.FoodMarketTheme
import com.hwaryun.designsystem.ui.asphalt.AsphaltTheme
import com.hwaryun.login.state.LoginState

@Composable
internal fun LoginRoute(
    navigateToSignUpScreen: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val loginState by viewModel.loginState.collectAsStateWithLifecycle()

    LoginScreen(
        loginState = loginState,
        navigateToSignUpScreen = navigateToSignUpScreen,
        onShowSnackbar = onShowSnackbar,
        updateEmailState = viewModel::updateEmailState,
        updatePasswordState = viewModel::updatePasswordState,
        updateIsPasswordVisible = viewModel::updateIsPasswordVisible,
        doSignIn = viewModel::signIn
    )
}

@Composable
fun LoginScreen(
    loginState: LoginState,
    navigateToSignUpScreen: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    updateEmailState: (String) -> Unit,
    updatePasswordState: (String) -> Unit,
    updateIsPasswordVisible: (Boolean) -> Unit,
    doSignIn: (String, String) -> Unit,
) {
    var shouldShowTrailingIcon by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(loginState) {
        if (loginState.error.isNotEmpty()) {
            onShowSnackbar(loginState.error, null)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = AsphaltTheme.colors.cool_gray_1cCp_50,
        topBar = {
            AsphaltAppBar(title = "Sign In")
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
                        .background(AsphaltTheme.colors.pure_white_500)
                        .verticalScroll(rememberScrollState())
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsphaltInputGroup(
                        value = loginState.email,
                        modifier = Modifier
                            .fillMaxWidth()
                            .onFocusChanged { shouldShowTrailingIcon = it.isFocused },
                        onValueChange = { updateEmailState(it) },
                        showLabel = true,
                        required = true,
                        label = "Email Address",
                        placeholder = "Type your email address",
                        singleLine = true,
                        isError = loginState.isEmailError,
                        errorMsg = if (loginState.isEmailError) stringResource(id = loginState.errorEmailMsg) else "",
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
                            if (loginState.email.isNotEmpty() && shouldShowTrailingIcon) {
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
                        value = loginState.password,
                        modifier = Modifier.fillMaxWidth(),
                        onValueChange = { updatePasswordState(it) },
                        showLabel = true,
                        required = true,
                        label = "Password",
                        placeholder = "Type your password",
                        singleLine = true,
                        visualTransformation = if (!loginState.isPasswordVisible) PasswordVisualTransformation() else VisualTransformation.None,
                        isError = loginState.isPasswordError,
                        errorMsg = if (loginState.isPasswordError) stringResource(id = loginState.errorPasswordMsg) else "",
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        trailingIcon = {
                            IconButton(
                                modifier = Modifier.size(20.dp),
                                onClick = { updateIsPasswordVisible(!loginState.isPasswordVisible) }
                            ) {
                                val icon = if (loginState.isPasswordVisible) {
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
                        isLoading = loginState.isLoading,
                        onClick = { doSignIn(loginState.email, loginState.password) }
                    ) {
                        AsphaltText(text = "Sign In")
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    AsphaltButton(
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !loginState.isLoading,
                        type = ButtonType.Outline,
                        onClick = { navigateToSignUpScreen() }
                    ) {
                        AsphaltText(text = "Create New Account")
                    }
                }

                //                if (loginState.isLoading) {
                //                    DialogBoxLoading()
                //                }
            }
        }
    )
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DefaultPreview() {
    FoodMarketTheme {
        LoginScreen(
            loginState = LoginState(),
            navigateToSignUpScreen = {},
            updateEmailState = {},
            updatePasswordState = {},
            updateIsPasswordVisible = {},
            doSignIn = { _, _ -> },
            onShowSnackbar = { _, _ -> true }
        )
    }
}