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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hwaryun.designsystem.components.DialogBoxLoading
import com.hwaryun.designsystem.components.FoodMarketButton
import com.hwaryun.designsystem.components.FoodMarketTextField
import com.hwaryun.designsystem.components.FoodMarketTopAppBar
import com.hwaryun.designsystem.ui.FoodMarketTheme
import com.hwaryun.signup.state.SignUpScreenState
import com.hwaryun.signup.state.SignUpState

@Composable
internal fun AddressRoute(
    popBackStack: () -> Unit,
    navigateToHomeScreen: () -> Unit,
    viewModel: SignUpViewModel
) {
    val signUpState = viewModel.signUpState.collectAsState()
    val screenState = viewModel.screenState.collectAsState()

    AddressScreen(
        popBackStack = popBackStack,
        navigateToHomeScreen = navigateToHomeScreen,
        signUpState = signUpState.value,
        screenState = screenState.value,
        updatePhoneNumberState = viewModel::updatePhoneNumberState,
        updateAddressState = viewModel::updateAddressState,
        updateHouseNumberState = viewModel::updateHouseNumberState,
        updateCityState = viewModel::updateCityState,
        doSignUp = viewModel::signUp,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressScreen(
    navigateToHomeScreen: () -> Unit,
    popBackStack: () -> Unit,
    signUpState: SignUpState,
    screenState: SignUpScreenState,
    updatePhoneNumberState: (String) -> Unit,
    updateAddressState: (String) -> Unit,
    updateHouseNumberState: (String) -> Unit,
    updateCityState: (String) -> Unit,
    doSignUp: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            FoodMarketTopAppBar(
                title = "Address",
                subtitle = "Make sure it’s valid",
                showNavigateBack = true,
                onNavigateBack = {
                    popBackStack()
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        content = { innerPadding ->

            LaunchedEffect(signUpState) {
                if (signUpState.signUp != null) {
                    navigateToHomeScreen()
                }

                if (signUpState.error.isNotBlank()) {
                    snackbarHostState.showSnackbar(signUpState.error)
                }
            }

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
                    FoodMarketTextField(
                        text = screenState.phoneNumber,
                        showLabel = true,
                        textLabel = "Phone No.",
                        placeholder = "Type your phone number",
                        isError = screenState.isPhoneNumberError,
                        errorMsg = if (screenState.isPhoneNumberError) stringResource(id = screenState.errorPhoneNumberMsg) else "",
                        onValueChange = { updatePhoneNumberState(it) },
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    FoodMarketTextField(
                        text = screenState.address,
                        showLabel = true,
                        textLabel = "Address",
                        placeholder = "Type your address",
                        isError = screenState.isAddressError,
                        errorMsg = if (screenState.isAddressError) stringResource(id = screenState.errorAddressMsg) else "",
                        onValueChange = { updateAddressState(it) },
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    FoodMarketTextField(
                        text = screenState.houseNumber,
                        showLabel = true,
                        textLabel = "House No.",
                        placeholder = "Type your house number",
                        isError = screenState.isHouseNumberError,
                        errorMsg = if (screenState.isHouseNumberError) stringResource(id = screenState.errorHouseNumberMsg) else "",
                        onValueChange = { updateHouseNumberState(it) },
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    val options =
                        listOf("Jakarta", "Bogor", "Depok", "Tangerang", "Bekasi", "Bandung")
                    var expanded by remember { mutableStateOf(false) }
                    var selectedOptionText by remember { mutableStateOf(options[0]) }
                    FoodMarketTextField(
                        text = screenState.city,
                        showLabel = true,
                        textLabel = "City",
                        placeholder = "Select your city",
                        isExpandedDropdown = true,
                        isError = screenState.isCityError,
                        errorMsg = if (screenState.isCityError) stringResource(id = screenState.errorCityMsg) else "",
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
                        readOnly = true,
                        options = options,
                        onValueChange = { updateCityState(it) },
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    FoodMarketButton(
                        text = "Sign Up Now",
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { doSignUp() }
                    )

                    if (signUpState.isLoading) {
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
            signUpState = SignUpState(),
            screenState = SignUpScreenState(),
            updatePhoneNumberState = {},
            updateAddressState = {},
            updateHouseNumberState = {},
            updateCityState = {},
            doSignUp = {},
        )
    }
}