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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hwaryun.designsystem.R
import com.hwaryun.designsystem.components.AsphaltAppBar
import com.hwaryun.designsystem.components.FoodMarketCircleImage
import com.hwaryun.designsystem.components.atoms.AsphaltButton
import com.hwaryun.designsystem.components.atoms.AsphaltText
import com.hwaryun.designsystem.components.molecules.AsphaltInputGroup
import com.hwaryun.designsystem.ui.FoodMarketTheme
import com.hwaryun.signup.state.RegisterState

@Composable
internal fun RegisterRoute(
    popBackStack: () -> Unit,
    navigateToAddressScreen: () -> Unit,
    viewModel: RegisterViewModel
) {
    val registerState by viewModel.registerState.collectAsStateWithLifecycle()

    RegisterScreen(
        registerState = registerState,
        popBackStack = popBackStack,
        navigateToAddressScreen = navigateToAddressScreen,
        updateNameState = viewModel::updateNameState,
        updateEmailState = viewModel::updateEmailState,
        updatePasswordState = viewModel::updatePasswordState,
        updateIsPasswordVisible = viewModel::updateIsPasswordVisible,
        validateFirstStep = viewModel::validateFirstStep,
    )
}

@Composable
fun RegisterScreen(
    registerState: RegisterState,
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

    if (registerState.firstStep != null && isFirstStepCompleted) {
        LaunchedEffect(Unit) {
            navigateToAddressScreen()
            isFirstStepCompleted = false
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AsphaltAppBar(
                title = "Daftar",
                showNavigateBack = true
            ) { popBackStack() }
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
                        value = registerState.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .onFocusChanged { shouldShowTrailingIcon = it.isFocused },
                        onValueChange = { updateNameState(it) },
                        showLabel = true,
                        required = true,
                        label = "Nama Lengkap",
                        placeholder = "Masukkan nama lengkap kamu",
                        singleLine = true,
                        isError = registerState.isNameError,
                        errorMsg = if (registerState.isNameError) stringResource(id = registerState.errorNameMsg) else "",
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
                            if (registerState.name.isNotEmpty() && shouldShowTrailingIcon) {
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
                        value = registerState.email,
                        modifier = Modifier
                            .fillMaxWidth()
                            .onFocusChanged { shouldShowTrailingIcon = it.isFocused },
                        onValueChange = { updateEmailState(it) },
                        showLabel = true,
                        required = true,
                        label = "Email",
                        placeholder = "Masukkan email kamu",
                        singleLine = true,
                        isError = registerState.isEmailError,
                        errorMsg = if (registerState.isEmailError) stringResource(id = registerState.errorEmailMsg) else "",
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
                            if (registerState.email.isNotEmpty() && shouldShowTrailingIcon) {
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
                        value = registerState.password,
                        modifier = Modifier.fillMaxWidth(),
                        onValueChange = { updatePasswordState(it) },
                        showLabel = true,
                        required = true,
                        label = "Kata Sandi",
                        placeholder = "Masukkan kata sandi kamu",
                        singleLine = true,
                        visualTransformation = if (!registerState.isPasswordVisible) PasswordVisualTransformation() else VisualTransformation.None,
                        isError = registerState.isPasswordError,
                        errorMsg = if (registerState.isPasswordError) stringResource(id = registerState.errorPasswordMsg) else "",
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        trailingIcon = {
                            IconButton(
                                modifier = Modifier.size(20.dp),
                                onClick = { updateIsPasswordVisible(!registerState.isPasswordVisible) }
                            ) {
                                val icon = if (registerState.isPasswordVisible) {
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
                                registerState.name,
                                registerState.email,
                                registerState.password
                            )
                            isFirstStepCompleted = true
                        }
                    ) {
                        AsphaltText(text = "Lanjut")
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
        RegisterScreen(
            popBackStack = {},
            navigateToAddressScreen = {},
            registerState = RegisterState(),
            updateNameState = {},
            updateEmailState = {},
            updatePasswordState = {},
            updateIsPasswordVisible = {},
            validateFirstStep = { _, _, _ -> },
        )
    }
}