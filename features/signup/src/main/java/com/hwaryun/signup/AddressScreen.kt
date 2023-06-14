package com.hwaryun.signup

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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hwaryun.designsystem.components.DialogBoxLoading
import com.hwaryun.designsystem.components.FoodMarketButton
import com.hwaryun.designsystem.components.FoodMarketTextField
import com.hwaryun.designsystem.components.FoodMarketTopAppBar
import com.hwaryun.designsystem.ui.FoodMarketTheme
import com.hwaryun.signup.state.SignUpState

@Composable
internal fun AddressRoute(
    popBackStack: () -> Unit,
    navigateToHomeScreen: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    viewModel: SignUpViewModel
) {
    val signUpState by viewModel.signUpState.collectAsStateWithLifecycle()

    AddressScreen(
        signUpState = signUpState,
        popBackStack = popBackStack,
        navigateToHomeScreen = navigateToHomeScreen,
        onShowSnackbar = onShowSnackbar,
        updatePhoneNumberState = viewModel::updatePhoneNumberState,
        updateAddressState = viewModel::updateAddressState,
        updateHouseNumberState = viewModel::updateHouseNumberState,
        updateCityState = viewModel::updateCityState,
        doSignUp = viewModel::signUp,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddressScreen(
    navigateToHomeScreen: () -> Unit,
    popBackStack: () -> Unit,
    signUpState: SignUpState,
    updatePhoneNumberState: (String) -> Unit,
    updateAddressState: (String) -> Unit,
    updateHouseNumberState: (String) -> Unit,
    updateCityState: (String) -> Unit,
    doSignUp: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean
) {
    LaunchedEffect(signUpState) {
        if (signUpState.signUp != null) {
            navigateToHomeScreen()
        }

        if (signUpState.error.isNotEmpty()) {
            onShowSnackbar(signUpState.error, null)
        }
    }

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
        content = {

            Box(
                modifier = Modifier
                    .padding(top = 24.dp)
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
                        text = signUpState.phoneNumber,
                        showLabel = true,
                        textLabel = "Phone No.",
                        placeholder = "Type your phone number",
                        isError = signUpState.isPhoneNumberError,
                        errorMsg = if (signUpState.isPhoneNumberError) stringResource(id = signUpState.errorPhoneNumberMsg) else "",
                        onValueChange = { updatePhoneNumberState(it) },
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    FoodMarketTextField(
                        text = signUpState.address,
                        showLabel = true,
                        textLabel = "Address",
                        placeholder = "Type your address",
                        isError = signUpState.isAddressError,
                        errorMsg = if (signUpState.isAddressError) stringResource(id = signUpState.errorAddressMsg) else "",
                        onValueChange = { updateAddressState(it) },
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    FoodMarketTextField(
                        text = signUpState.houseNumber,
                        showLabel = true,
                        textLabel = "House No.",
                        placeholder = "Type your house number",
                        isError = signUpState.isHouseNumberError,
                        errorMsg = if (signUpState.isHouseNumberError) stringResource(id = signUpState.errorHouseNumberMsg) else "",
                        onValueChange = { updateHouseNumberState(it) },
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    val options = listOf("Jakarta", "Bogor", "Depok", "Tangerang", "Bekasi", "Bandung")
                    var expanded by remember { mutableStateOf(false) }
                    FoodMarketTextField(
                        text = signUpState.city,
                        showLabel = true,
                        textLabel = "City",
                        placeholder = "Select your city",
                        isExpandedDropdown = true,
                        isError = signUpState.isCityError,
                        errorMsg = if (signUpState.isCityError) stringResource(id = signUpState.errorCityMsg) else "",
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
            updatePhoneNumberState = {},
            updateAddressState = {},
            updateHouseNumberState = {},
            updateCityState = {},
            doSignUp = {},
            onShowSnackbar = { _, _ -> true },
        )
    }
}