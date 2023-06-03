package com.hwaryun.profile

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hwaryun.designsystem.component.FoodMarketTabSection
import com.hwaryun.designsystem.component.TabItem
import com.hwaryun.designsystem.ui.FoodMarketTheme
import com.hwaryun.profile.components.HeaderProfile

@Composable
internal fun ProfileRoute() {
    ProfileScreen()
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ProfileScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            HeaderProfile(modifier = Modifier.fillMaxWidth())
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
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    FoodMarketTheme {
        ProfileScreen()
    }
}

@Preview(showBackground = true, widthDp = 480)
@Composable
private fun PortraitPreview() {
    FoodMarketTheme {
        ProfileScreen()
    }
}