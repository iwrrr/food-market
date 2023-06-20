package com.hwaryun.signin

import android.annotation.SuppressLint
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
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
import com.hwaryun.designsystem.components.CustomTextField
import com.hwaryun.designsystem.components.DialogBoxLoading
import com.hwaryun.designsystem.components.FoodMarketButton
import com.hwaryun.designsystem.components.FoodMarketTopAppBar
import com.hwaryun.designsystem.ui.Black500
import com.hwaryun.designsystem.ui.FoodMarketTheme
import com.hwaryun.designsystem.utils.ButtonType
import com.hwaryun.signin.state.SignInUiState

@Composable
internal fun SignInRoute(
    navigateToSignUpScreen: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    viewModel: SignInViewModel = hiltViewModel()
) {
    val signInUiState by viewModel.signInUiState.collectAsStateWithLifecycle()

    SignInScreen(
        signInUiState = signInUiState,
        navigateToSignUpScreen = navigateToSignUpScreen,
        onShowSnackbar = onShowSnackbar,
        updateEmailState = viewModel::updateEmailState,
        updatePasswordState = viewModel::updatePasswordState,
        updateIsPasswordVisible = viewModel::updateIsPasswordVisible,
        doSignIn = viewModel::signIn
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun SignInScreen(
    signInUiState: SignInUiState,
    navigateToSignUpScreen: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    updateEmailState: (String) -> Unit,
    updatePasswordState: (String) -> Unit,
    updateIsPasswordVisible: (Boolean) -> Unit,
    doSignIn: (String, String) -> Unit,
) {
    val focusManager = LocalFocusManager.current

    LaunchedEffect(signInUiState) {
        if (signInUiState.error.isNotEmpty()) {
            onShowSnackbar(signInUiState.error, null)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            FoodMarketTopAppBar(
                title = "Sign In",
                subtitle = "Find your best ever meal"
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
                    CustomTextField(
                        value = signInUiState.email,
                        onValueChange = { updateEmailState(it) },
                        showLabel = true,
                        label = "Email Address",
                        placeholder = "Type your email address",
                        singleLine = true,
                        isError = signInUiState.isEmailError,
                        errorMsg = if (signInUiState.isEmailError) stringResource(id = signInUiState.errorEmailMsg) else "",
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
                            if (signInUiState.email.isNotEmpty()) {
                                IconButton(
                                    modifier = Modifier.size(20.dp),
                                    onClick = { updateEmailState("") }
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_close_circle_bold),
                                        tint = Black500,
                                        contentDescription = "Clear Text"
                                    )
                                }
                            }
                        },
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    CustomTextField(
                        value = signInUiState.password,
                        onValueChange = { updatePasswordState(it) },
                        showLabel = true,
                        label = "Password",
                        placeholder = "Type your password",
                        singleLine = true,
                        visualTransformation = if (!signInUiState.isPasswordVisible) PasswordVisualTransformation() else VisualTransformation.None,
                        isError = signInUiState.isPasswordError,
                        errorMsg = if (signInUiState.isPasswordError) stringResource(id = signInUiState.errorPasswordMsg) else "",
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        trailingIcon = {
                            IconButton(
                                modifier = Modifier.size(20.dp),
                                onClick = { updateIsPasswordVisible(!signInUiState.isPasswordVisible) }
                            ) {
                                val icon = if (signInUiState.isPasswordVisible) {
                                    painterResource(id = R.drawable.ic_eye_slash_bold)
                                } else {
                                    painterResource(id = R.drawable.ic_eye_bold)
                                }

                                Icon(
                                    painter = icon,
                                    tint = Black500,
                                    contentDescription = "Password Toggle"
                                )
                            }
                        },
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    FoodMarketButton(
                        text = "Sign In",
                        modifier = Modifier.fillMaxWidth(),
                        type = ButtonType.Primary,
                        onClick = { doSignIn(signInUiState.email, signInUiState.password) }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    FoodMarketButton(
                        text = "Create New Account",
                        modifier = Modifier.fillMaxWidth(),
                        type = ButtonType.Outline,
                        onClick = { navigateToSignUpScreen() }
                    )
                }

                if (signInUiState.isLoading) {
                    DialogBoxLoading()
                }
            }
        }
    )
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DefaultPreview() {
    FoodMarketTheme {
        SignInScreen(
            signInUiState = SignInUiState(),
            navigateToSignUpScreen = {},
            updateEmailState = {},
            updatePasswordState = {},
            updateIsPasswordVisible = {},
            doSignIn = { _, _ -> },
            onShowSnackbar = { _, _ -> true }
        )
    }
}