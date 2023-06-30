package com.hwaryun.onboarding

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.hwaryun.designsystem.R
import com.hwaryun.designsystem.components.atoms.AsphaltButton
import com.hwaryun.designsystem.components.atoms.AsphaltText
import com.hwaryun.designsystem.ui.FoodMarketTheme
import com.hwaryun.designsystem.ui.asphalt.AsphaltTheme

@Composable
internal fun OnBoardingRoute(
    viewModel: OnBoardingViewModel = hiltViewModel()
) {

    OnBoardingScreen(
        saveOnBoardingState = viewModel::saveOnBoardingState
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun OnBoardingScreen(
    saveOnBoardingState: (Boolean) -> Unit,
) {
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = !isSystemInDarkTheme()

    DisposableEffect(systemUiController, useDarkIcons) {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent
        )

        onDispose {
            systemUiController.setSystemBarsColor(
                color = Color.Transparent,
                darkIcons = true
            )
        }
    }

    Scaffold(
        containerColor = AsphaltTheme.colors.pure_white_500,
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .background(AsphaltTheme.colors.gojek_green_500)
                        .fillMaxWidth()
                        .weight(3f),
                    contentAlignment = Alignment.Center,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_order_empty),
                        contentDescription = null,
                        modifier = Modifier.size(200.dp)
                    )
                }
                Spacer(modifier = Modifier.height(32.dp))
                AsphaltText(
                    text = "Kamu Lapar?",
                    style = AsphaltTheme.typography.titleExtraLarge
                )
                Spacer(modifier = Modifier.height(16.dp))
                AsphaltText(
                    text = "Semua restoran terbaik mereka dengan menu teratas menunggu Anda, mereka tidak sabar menunggu pesanan Anda!",
                    modifier = Modifier.fillMaxWidth(3 / 4f),
                    style = AsphaltTheme.typography.titleModerateDemi,
                    color = AsphaltTheme.colors.cool_gray_500,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.weight(1f))
                Column(
                    modifier = Modifier.padding(start = 24.dp, end = 24.dp, bottom = 32.dp)
                ) {
                    AsphaltButton(onClick = { saveOnBoardingState(true) }) {
                        AsphaltText(text = "Ayo Mulai")
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
        OnBoardingScreen {}
    }
}