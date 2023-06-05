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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hwaryun.designsystem.component.ButtonType
import com.hwaryun.designsystem.component.FoodMarketButton
import com.hwaryun.designsystem.component.FoodMarketTextField
import com.hwaryun.designsystem.component.FoodMarketTopAppBar
import com.hwaryun.designsystem.ui.FoodMarketTheme

@Composable
internal fun SignInRoute(
    navigateToSignUpScreen: () -> Unit,
    navigateToHomeScreen: () -> Unit
) {
    SignInScreen(
        navigateToSignUpScreen = navigateToSignUpScreen,
        navigateToHomeScreen = navigateToHomeScreen
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun SignInScreen(
    navigateToSignUpScreen: () -> Unit,
    navigateToHomeScreen: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            FoodMarketTopAppBar(title = "Sign In", subtitle = "Find your best ever meal")
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
                    FoodMarketTextField(
                        text = "",
                        showLabel = true,
                        textLabel = "Email Address",
                        placeholder = "Type your email address",
                        isPasswordTextField = false,
                        onValueChange = {},
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    FoodMarketTextField(
                        text = "",
                        showLabel = true,
                        textLabel = "Password",
                        placeholder = "Type your password",
                        isPasswordTextField = true,
                        onValueChange = {}
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    FoodMarketButton(
                        text = "Sign In",
                        modifier = Modifier.fillMaxWidth(),
                        type = ButtonType.Primary,
                        onClick = { navigateToHomeScreen() }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    FoodMarketButton(
                        text = "Create New Account",
                        modifier = Modifier.fillMaxWidth(),
                        type = ButtonType.Secondary,
                        onClick = { navigateToSignUpScreen() }
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
        SignInScreen(
            navigateToSignUpScreen = {},
            navigateToHomeScreen = {}
        )
    }
}