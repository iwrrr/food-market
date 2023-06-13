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
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hwaryun.designsystem.components.FoodMarketButton
import com.hwaryun.designsystem.components.FoodMarketCircleImage
import com.hwaryun.designsystem.components.FoodMarketTextField
import com.hwaryun.designsystem.components.FoodMarketTopAppBar
import com.hwaryun.designsystem.ui.FoodMarketTheme
import com.hwaryun.signup.state.SignUpState

@Composable
internal fun SignUpRoute(
    popBackStack: () -> Unit,
    navigateToAddressScreen: () -> Unit,
    viewModel: SignUpViewModel
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    SignUpScreen(
        popBackStack = popBackStack,
        navigateToAddressScreen = navigateToAddressScreen,
        uiState = uiState.value,
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
    popBackStack: () -> Unit,
    navigateToAddressScreen: () -> Unit,
    uiState: SignUpState,
    updateNameState: (String) -> Unit,
    updateEmailState: (String) -> Unit,
    updatePasswordState: (String) -> Unit,
    updateIsPasswordVisible: (Boolean) -> Unit,
    validateFirstStep: (String, String, String) -> Unit,
) {
    var isFirstStepCompleted by rememberSaveable { mutableStateOf(true) }

    if (uiState.firstStep != null && isFirstStepCompleted) {
        LaunchedEffect(Unit) {
            navigateToAddressScreen()
            isFirstStepCompleted = false
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            FoodMarketTopAppBar(
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
                    Spacer(modifier = Modifier.height(16.dp))
                    FoodMarketTextField(
                        text = uiState.name,
                        showLabel = true,
                        textLabel = "Full Name",
                        placeholder = "Type your full name",
                        isError = uiState.isNameError,
                        errorMsg = if (uiState.isNameError) stringResource(id = uiState.errorNameMsg) else "",
                        onValueChange = { updateNameState(it) },
                    )
                    Spacer(modifier = Modifier.height(8.dp))
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
                        text = "Continue",
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            validateFirstStep(
                                uiState.name,
                                uiState.email,
                                uiState.password
                            )
                            isFirstStepCompleted = true
                        }
                    )
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
            uiState = SignUpState(),
            updateNameState = {},
            updateEmailState = {},
            updatePasswordState = {},
            updateIsPasswordVisible = {},
            validateFirstStep = { _, _, _ -> },
        )
    }
}