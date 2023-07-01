package com.hwaryun.signup

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
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
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hwaryun.designsystem.R
import com.hwaryun.designsystem.components.AsphaltAppBar
import com.hwaryun.designsystem.components.AsphaltDropdown
import com.hwaryun.designsystem.components.atoms.AsphaltButton
import com.hwaryun.designsystem.components.atoms.AsphaltText
import com.hwaryun.designsystem.components.molecules.AsphaltInputGroup
import com.hwaryun.designsystem.ui.FoodMarketTheme
import com.hwaryun.designsystem.ui.asphalt.AsphaltTheme
import com.hwaryun.signup.state.RegisterState

@Composable
internal fun AddressRoute(
    popBackStack: () -> Unit,
    navigateToHomeScreen: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    viewModel: RegisterViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val isOffline by viewModel.isOffline.collectAsStateWithLifecycle()

    AddressScreen(
        state = state,
        isOffline = isOffline,
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
    state: RegisterState,
    isOffline: Boolean,
    navigateToHomeScreen: () -> Unit,
    popBackStack: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    updatePhoneNumberState: (String) -> Unit,
    updateAddressState: (String) -> Unit,
    updateHouseNumberState: (String) -> Unit,
    updateCityState: (String) -> Unit,
    resetErrorState: () -> Unit,
    doSignUp: () -> Unit,
) {
    var shouldShowTrailingIcon by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(state) {
        if (state.signUp != null) {
            navigateToHomeScreen()
        }

        if (state.error.isNotEmpty()) {
            onShowSnackbar(state.error, null)
            resetErrorState()
        }
    }

    val notConnectedMessage = stringResource(id = R.string.not_connected_message)

    LaunchedEffect(isOffline) {
        if (isOffline) {
            onShowSnackbar(notConnectedMessage, null)
        }
    }

    BackHandler(state.isLoading) {
        return@BackHandler
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        containerColor = AsphaltTheme.colors.cool_gray_1cCp_50,
        topBar = {
            AsphaltAppBar(
                title = stringResource(id = R.string.title_address),
                showNavigateBack = true,
                onNavigateBack = {
                    if (!state.isLoading) popBackStack()
                }
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
                        .background(AsphaltTheme.colors.pure_white_500)
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsphaltInputGroup(
                        value = state.phoneNumber,
                        modifier = Modifier
                            .fillMaxWidth()
                            .onFocusChanged { shouldShowTrailingIcon = it.isFocused },
                        onValueChange = { updatePhoneNumberState(it) },
                        showLabel = true,
                        required = true,
                        label = stringResource(id = R.string.label_phone_number),
                        placeholder = stringResource(id = R.string.placeholder_phone_number),
                        singleLine = true,
                        isError = state.isPhoneNumberError,
                        errorMsg = if (state.isPhoneNumberError) stringResource(id = state.errorPhoneNumberMsg) else "",
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
                            if (state.phoneNumber.isNotEmpty() && shouldShowTrailingIcon) {
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
                        value = state.address,
                        modifier = Modifier
                            .fillMaxWidth()
                            .onFocusChanged { shouldShowTrailingIcon = it.isFocused },
                        onValueChange = { updateAddressState(it) },
                        showLabel = true,
                        required = true,
                        label = stringResource(id = R.string.label_address),
                        placeholder = stringResource(id = R.string.placeholder_address),
                        singleLine = true,
                        isError = state.isAddressError,
                        errorMsg = if (state.isAddressError) stringResource(id = state.errorAddressMsg) else "",
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
                            if (state.address.isNotEmpty() && shouldShowTrailingIcon) {
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
                        value = state.houseNumber,
                        modifier = Modifier
                            .fillMaxWidth()
                            .onFocusChanged { shouldShowTrailingIcon = it.isFocused },
                        onValueChange = { updateHouseNumberState(it) },
                        showLabel = true,
                        required = true,
                        label = stringResource(id = R.string.label_house_number),
                        placeholder = stringResource(id = R.string.placeholder_house_number),
                        singleLine = true,
                        isError = state.isHouseNumberError,
                        errorMsg = if (state.isHouseNumberError) stringResource(id = state.errorHouseNumberMsg) else "",
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
                            if (state.houseNumber.isNotEmpty() && shouldShowTrailingIcon) {
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

                    val cities = stringArrayResource(id = R.array.cities).toList()
                    var expanded by remember { mutableStateOf(false) }
                    AsphaltDropdown(
                        text = state.city,
                        showLabel = true,
                        label = stringResource(id = R.string.label_city),
                        placeholder = stringResource(id = R.string.placeholder_city),
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded },
                        onDismissRequest = { expanded = false },
                        onClickExpanded = {
                            updateCityState(it)
                            expanded = false
                        },
                        options = cities,
                        isError = state.isCityError,
                        readOnly = true,
                        onValueChange = { updateCityState(it) },
                        errorMsg = if (state.isCityError) stringResource(id = state.errorCityMsg) else "",
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    AsphaltButton(
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !state.isLoading && !isOffline,
                        isLoading = state.isLoading,
                        onClick = { doSignUp() }
                    ) {
                        AsphaltText(text = stringResource(id = R.string.btn_register))
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
            state = RegisterState(),
            isOffline = false,
            navigateToHomeScreen = {},
            popBackStack = {},
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