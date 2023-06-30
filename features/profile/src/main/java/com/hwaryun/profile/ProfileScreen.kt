package com.hwaryun.profile

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hwaryun.designsystem.components.DialogBoxLoading
import com.hwaryun.designsystem.components.FoodMarketTabSection
import com.hwaryun.designsystem.components.TabItem
import com.hwaryun.designsystem.components.atoms.AsphaltButton
import com.hwaryun.designsystem.components.atoms.AsphaltText
import com.hwaryun.designsystem.components.atoms.ButtonType
import com.hwaryun.designsystem.ui.FoodMarketTheme
import com.hwaryun.designsystem.ui.asphalt.AsphaltTheme
import com.hwaryun.profile.components.HeaderProfile

@Composable
internal fun ProfileRoute(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val profileState by viewModel.profileState.collectAsStateWithLifecycle()

    ProfileScreen(
        state = profileState,
        onShowSnackbar = onShowSnackbar,
        onLogoutClick = viewModel::logout,
        resetErrorState = viewModel::resetErrorState
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProfileScreen(
    state: ProfileState,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    onLogoutClick: () -> Unit,
    resetErrorState: () -> Unit,
) {
    LaunchedEffect(state) {
        if (state.error.isNotEmpty()) {
            onShowSnackbar(state.error, null)
            resetErrorState()
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        containerColor = AsphaltTheme.colors.pure_white_500
    ) {
        Box(
            modifier = Modifier
                .padding(bottom = 58.dp)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                HeaderProfile(
                    modifier = Modifier.fillMaxWidth(),
                    uiState = state,
                )
                Spacer(modifier = Modifier.height(24.dp))
                FoodMarketTabSection(
                    tabItems = listOf(
                        TabItem(
                            title = "Akun",
                            screen = { AccountScreen() }
                        ),
                        TabItem(
                            title = "FoodMarket",
                            screen = { FoodMarketScreen() }
                        )
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp, end = 24.dp, bottom = 32.dp)
                ) {
                    AsphaltButton(
                        modifier = Modifier.fillMaxWidth(),
                        type = ButtonType.Outline,
                        onClick = { onLogoutClick() },
                    ) {
                        AsphaltText(text = "Logout")
                    }
                }
            }

            if (state.isLoading) {
                DialogBoxLoading()
            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DefaultPreview() {
    FoodMarketTheme {
        ProfileScreen(
            state = ProfileState(),
            onShowSnackbar = { _, _ -> false },
            onLogoutClick = {},
            resetErrorState = {},
        )
    }
}