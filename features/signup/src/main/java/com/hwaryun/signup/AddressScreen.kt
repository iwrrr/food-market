package com.hwaryun.signup

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.widget.Toast
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hwaryun.designsystem.component.FoodMarketButton
import com.hwaryun.designsystem.component.FoodMarketTextField
import com.hwaryun.designsystem.component.FoodMarketTopAppBar
import com.hwaryun.designsystem.ui.FoodMarketTheme

@Composable
internal fun AddressRoute(
    popBackStack: () -> Unit,
    navigateToHomeScreen: () -> Unit
) {
    AddressScreen(
        popBackStack = popBackStack,
        navigateToHomeScreen = navigateToHomeScreen
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressScreen(
    navigateToHomeScreen: () -> Unit,
    popBackStack: () -> Unit
) {
    val context = LocalContext.current

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
                    FoodMarketTextField(
                        text = "",
                        showLabel = true,
                        textLabel = "Phone No.",
                        placeholder = "Type your phone number",
                        isPasswordTextField = false,
                        onValueChange = {},
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    FoodMarketTextField(
                        text = "",
                        showLabel = true,
                        textLabel = "Address",
                        placeholder = "Type your address",
                        isPasswordTextField = false,
                        onValueChange = {},
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    FoodMarketTextField(
                        text = "",
                        showLabel = true,
                        textLabel = "House No.",
                        placeholder = "Type your house number",
                        isPasswordTextField = false,
                        onValueChange = {}
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    val options = listOf("Option 1", "Option 2", "Option 3", "Option 4", "Option 5")
                    var expanded by remember { mutableStateOf(false) }
                    var selectedOptionText by remember { mutableStateOf(options[0]) }
                    FoodMarketTextField(
                        text = selectedOptionText,
                        showLabel = true,
                        textLabel = "City",
                        placeholder = "Select your city",
                        isPasswordTextField = false,
                        isExpandedDropdown = true,
                        expanded = expanded,
                        onExpandedChange = {
                            expanded = !expanded
                        },
                        onDismissRequest = {
                            expanded = false
                        },
                        onClickExpanded = {
                            selectedOptionText = it
                            expanded = false
                        },
                        readOnly = true,
                        options = options,
                        onValueChange = { Toast.makeText(context, it, Toast.LENGTH_SHORT).show() }
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    FoodMarketButton(
                        text = "Sign Up Now",
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { navigateToHomeScreen() }
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
        AddressScreen(
            navigateToHomeScreen = {},
            popBackStack = {}
        )
    }
}