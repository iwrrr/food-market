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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hwaryun.designsystem.components.ButtonType
import com.hwaryun.designsystem.components.DialogBoxLoading
import com.hwaryun.designsystem.components.FoodMarketButton
import com.hwaryun.designsystem.components.FoodMarketTextField
import com.hwaryun.designsystem.components.FoodMarketTopAppBar
import com.hwaryun.designsystem.ui.FoodMarketTheme
import com.hwaryun.signin.state.SignInState

@Composable
internal fun SignInRoute(
    navigateToSignUpScreen: () -> Unit,
    viewModel: SignInViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    SignInScreen(
        navigateToSignUpScreen = navigateToSignUpScreen,
        uiState = uiState.value,
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
    navigateToSignUpScreen: () -> Unit,
    uiState: SignInState,
    updateEmailState: (String) -> Unit,
    updatePasswordState: (String) -> Unit,
    updateIsPasswordVisible: (Boolean) -> Unit,
    doSignIn: (String, String) -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            FoodMarketTopAppBar(
                title = "Sign In",
                subtitle = "Find your best ever meal"
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        content = { innerPadding ->

            LaunchedEffect(key1 = uiState) {
                if (uiState.error.isNotBlank()) {
                    snackbarHostState.showSnackbar(uiState.error)
                }
            }

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
                    FoodMarketTextField(
                        text = uiState.email,
                        showLabel = true,
                        textLabel = "Email Address",
                        placeholder = "Type your email address",
                        isError = uiState.isEmailError,
                        errorMsg = if (uiState.isEmailError) stringResource(id = uiState.errorEmailMsg) else "",
                        onValueChange = { updateEmailState(it) },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    FoodMarketTextField(
                        text = uiState.password,
                        showLabel = true,
                        textLabel = "Password",
                        placeholder = "Type your password",
                        isPasswordTextField = !uiState.isPasswordVisible,
                        isError = uiState.isPasswordError,
                        errorMsg = if (uiState.isPasswordError) stringResource(id = uiState.errorPasswordMsg) else "",
                        trailingIcon = {
                            IconButton(
                                onClick = { updateIsPasswordVisible(!uiState.isPasswordVisible) }
                            ) {
                                Icon(
                                    imageVector = if (uiState.isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    tint = Color.Gray,
                                    contentDescription = "Password Toggle"
                                )
                            }
                        },
                        onValueChange = { updatePasswordState(it) }
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    FoodMarketButton(
                        text = "Sign In",
                        modifier = Modifier.fillMaxWidth(),
                        type = ButtonType.Primary,
                        onClick = { doSignIn(uiState.email, uiState.password) }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    FoodMarketButton(
                        text = "Create New Account",
                        modifier = Modifier.fillMaxWidth(),
                        type = ButtonType.Secondary,
                        onClick = { navigateToSignUpScreen() }
                    )
                }

                if (uiState.isLoading) {
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
            navigateToSignUpScreen = {},
            uiState = SignInState(),
            updateEmailState = {},
            updatePasswordState = {},
            updateIsPasswordVisible = {},
            doSignIn = { _, _ -> }
        )
    }
}