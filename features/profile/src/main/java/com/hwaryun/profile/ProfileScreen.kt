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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hwaryun.designsystem.components.ButtonType
import com.hwaryun.designsystem.components.DialogBoxLoading
import com.hwaryun.designsystem.components.FoodMarketButton
import com.hwaryun.designsystem.components.FoodMarketTabSection
import com.hwaryun.designsystem.components.TabItem
import com.hwaryun.designsystem.ui.FoodMarketTheme
import com.hwaryun.profile.components.HeaderProfile

@Composable
internal fun ProfileRoute(
    onLogoutClick: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    ProfileScreen(
        uiState = uiState.value,
        onLogoutClick = viewModel::logout
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProfileScreen(
    uiState: ProfileState,
    onLogoutClick: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
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
                    uiState = uiState,
                )
                Spacer(modifier = Modifier.height(24.dp))
                FoodMarketTabSection(
                    tabItems = listOf(
                        TabItem(
                            title = "Account",
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
                        .padding(24.dp)
                ) {
                    FoodMarketButton(
                        modifier = Modifier.fillMaxWidth(),
                        type = ButtonType.Error,
                        text = "Logout",
                        onClick = { onLogoutClick() },
                    )
                }
            }

            if (uiState.isLoading) {
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
            uiState = ProfileState(),
            onLogoutClick = {}
        )
    }
}