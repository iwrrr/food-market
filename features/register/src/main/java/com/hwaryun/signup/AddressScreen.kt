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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hwaryun.designsystem.R
import com.hwaryun.designsystem.components.AsphaltAppBar
import com.hwaryun.designsystem.components.AsphaltDropdown
import com.hwaryun.designsystem.components.DialogBoxLoading
import com.hwaryun.designsystem.components.atoms.AsphaltButton
import com.hwaryun.designsystem.components.atoms.AsphaltText
import com.hwaryun.designsystem.components.molecules.AsphaltInputGroup
import com.hwaryun.designsystem.ui.FoodMarketTheme
import com.hwaryun.signup.state.RegisterState

@Composable
internal fun AddressRoute(
    popBackStack: () -> Unit,
    navigateToHomeScreen: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    viewModel: RegisterViewModel
) {
    val signUpState by viewModel.registerState.collectAsStateWithLifecycle()

    AddressScreen(
        registerState = signUpState,
        popBackStack = popBackStack,
        navigateToHomeScreen = navigateToHomeScreen,
        onShowSnackbar = onShowSnackbar,
        updatePhoneNumberState = viewModel::updatePhoneNumberState,
        updateAddressState = viewModel::updateAddressState,
        updateHouseNumberState = viewModel::updateHouseNumberState,
        updateCityState = viewModel::updateCityState,
        resetErrorState = viewModel::resetErrorState,
        doSignUp = viewModel::signUp,
    )
}

@Composable
fun AddressScreen(
    navigateToHomeScreen: () -> Unit,
    popBackStack: () -> Unit,
    registerState: RegisterState,
    updatePhoneNumberState: (String) -> Unit,
    updateAddressState: (String) -> Unit,
    updateHouseNumberState: (String) -> Unit,
    updateCityState: (String) -> Unit,
    resetErrorState: () -> Unit,
    doSignUp: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    var shouldShowTrailingIcon by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(registerState) {
        if (registerState.signUp != null) {
            navigateToHomeScreen()
        }

        if (registerState.error.isNotEmpty()) {
            onShowSnackbar(registerState.error, null)
            resetErrorState()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AsphaltAppBar(
                title = "Alamat",
                showNavigateBack = true
            ) {
                popBackStack()
            }
        },
        content = { innerPadding ->

            Box(
                modifier = Modifier
                    .padding(top = innerPadding.calculateTopPadding() + 24.dp)
                    .fillMaxSize(),
            ) {
                Column(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surface)
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsphaltInputGroup(
                        value = registerState.phoneNumber,
                        modifier = Modifier
                            .fillMaxWidth()
                            .onFocusChanged { shouldShowTrailingIcon = it.isFocused },
                        onValueChange = { updatePhoneNumberState(it) },
                        showLabel = true,
                        required = true,
                        label = "No. Telepon",
                        placeholder = "Masukkan nomor telepon kamu",
                        singleLine = true,
                        isError = registerState.isPhoneNumberError,
                        errorMsg = if (registerState.isPhoneNumberError) stringResource(id = registerState.errorPhoneNumberMsg) else "",
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Phone,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = {
                                focusManager.moveFocus(FocusDirection.Down)
                            }
                        ),
                        trailingIcon = {
                            if (registerState.phoneNumber.isNotEmpty() && shouldShowTrailingIcon) {
                                IconButton(
                                    modifier = Modifier.size(20.dp),
                                    onClick = { updatePhoneNumberState("") }
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
                        value = registerState.address,
                        modifier = Modifier
                            .fillMaxWidth()
                            .onFocusChanged { shouldShowTrailingIcon = it.isFocused },
                        onValueChange = { updateAddressState(it) },
                        showLabel = true,
                        required = true,
                        label = "Alamat",
                        placeholder = "Masukkan alamat kamu",
                        singleLine = true,
                        isError = registerState.isAddressError,
                        errorMsg = if (registerState.isAddressError) stringResource(id = registerState.errorAddressMsg) else "",
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
                            if (registerState.address.isNotEmpty() && shouldShowTrailingIcon) {
                                IconButton(
                                    modifier = Modifier.size(20.dp),
                                    onClick = { updateAddressState("") }
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
                        value = registerState.houseNumber,
                        modifier = Modifier
                            .fillMaxWidth()
                            .onFocusChanged { shouldShowTrailingIcon = it.isFocused },
                        onValueChange = { updateHouseNumberState(it) },
                        showLabel = true,
                        required = true,
                        label = "No. Rumah",
                        placeholder = "Masukkan nomor rumah kamu",
                        singleLine = true,
                        isError = registerState.isHouseNumberError,
                        errorMsg = if (registerState.isHouseNumberError) stringResource(id = registerState.errorHouseNumberMsg) else "",
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
                            if (registerState.houseNumber.isNotEmpty() && shouldShowTrailingIcon) {
                                IconButton(
                                    modifier = Modifier.size(20.dp),
                                    onClick = { updateHouseNumberState("") }
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

                    val options =
                        listOf("Jakarta", "Bogor", "Depok", "Tangerang", "Bekasi", "Bandung")
                    var expanded by remember { mutableStateOf(false) }
                    AsphaltDropdown(
                        text = registerState.city,
                        showLabel = true,
                        textLabel = "Kota",
                        placeholder = "Pilih kota",
                        expanded = expanded,
                        onExpandedChange = {
                            expanded = !expanded
                        },
                        onDismissRequest = {
                            expanded = false
                        },
                        onClickExpanded = {
                            updateCityState(it)
                            expanded = false
                        },
                        options = options,
                        isError = registerState.isCityError,
                        readOnly = true,
                        onValueChange = { updateCityState(it) },
                        errorMsg = if (registerState.isCityError) stringResource(id = registerState.errorCityMsg) else "",
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    AsphaltButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { doSignUp() }
                    ) {
                        AsphaltText(text = "Daftar Sekarang")
                    }

                    if (registerState.isLoading) {
                        DialogBoxLoading()
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
        AddressScreen(
            navigateToHomeScreen = {},
            popBackStack = {},
            registerState = RegisterState(),
            updatePhoneNumberState = {},
            updateAddressState = {},
            updateHouseNumberState = {},
            updateCityState = {},
            resetErrorState = {},
            doSignUp = {},
            onShowSnackbar = { _, _ -> true },
        )
    }
}