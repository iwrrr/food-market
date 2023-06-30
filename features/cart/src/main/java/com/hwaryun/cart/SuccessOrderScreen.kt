package com.hwaryun.cart

import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.hwaryun.common.ext.toNumberFormat
import com.hwaryun.designsystem.R
import com.hwaryun.designsystem.components.atoms.AsphaltButton
import com.hwaryun.designsystem.components.atoms.AsphaltText
import com.hwaryun.designsystem.components.atoms.ButtonType
import com.hwaryun.designsystem.ui.FoodMarketTheme
import com.hwaryun.designsystem.ui.asphalt.AsphaltTheme
import com.hwaryun.designsystem.ui.asphalt.LocalContentColor

@Composable
internal fun SuccessOrderRoute(
    foodName: String,
    totalPrice: Int,
    navigateToHome: () -> Unit,
) {
    SuccessOrderScreen(
        foodName = foodName,
        totalPrice = totalPrice,
        navigateToHome = navigateToHome,
    )
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun SuccessOrderScreen(
    foodName: String,
    totalPrice: Int,
    navigateToHome: () -> Unit,
) {
    val context = LocalContext.current
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

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = AsphaltTheme.colors.pure_white_500
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .background(AsphaltTheme.colors.gojek_green_500)
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .weight(3f),
                contentAlignment = Alignment.TopStart,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.height(120.dp))
                    Image(
                        painter = painterResource(id = R.drawable.ic_check_mark),
                        contentDescription = null,
                        modifier = Modifier.size(150.dp)
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    AsphaltText(
                        text = "Pembayaran berhasil",
                        modifier = Modifier.fillMaxWidth(),
                        style = AsphaltTheme.typography.titleLarge,
                        color = AsphaltTheme.colors.pure_white_500,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    AsphaltText(
                        text = "Kamu telah menyelesaikan pembayaran",
                        modifier = Modifier.fillMaxWidth(),
                        style = AsphaltTheme.typography.titleSmallDemi,
                        color = AsphaltTheme.colors.pure_white_500,
                        textAlign = TextAlign.Center
                    )
                }
                IconButton(onClick = { navigateToHome() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_close_circle_bold),
                        contentDescription = null,
                        tint = AsphaltTheme.colors.pure_white_500
                    )
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            AsphaltText(
                text = "Total Bayar",
                modifier = Modifier.fillMaxWidth(),
                style = AsphaltTheme.typography.titleSmallDemi,
                color = AsphaltTheme.colors.cool_gray_500,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            AsphaltText(
                text = "Rp ${totalPrice.toNumberFormat()}",
                modifier = Modifier.fillMaxWidth(),
                style = AsphaltTheme.typography.titleExtraLarge,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.weight(1f))
            Column(
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 32.dp),
            ) {
                AsphaltButton(
                    modifier = Modifier.fillMaxWidth(),
                    type = ButtonType.Primary,
                    onClick = {
                        val sendIntent: Intent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(
                                Intent.EXTRA_TEXT,
                                "Berhasil melakukan pembayaran untuk $foodName sebesar Rp ${totalPrice.toNumberFormat()}"
                            )
                            type = "text/plain"
                        }

                        val shareIntent = Intent.createChooser(sendIntent, null)
                        context.startActivity(shareIntent)
                    },
                ) {
                    AsphaltText(text = "Bagikan Transaksi")
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.ic_direct_send),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = LocalContentColor.current
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    FoodMarketTheme {
        SuccessOrderScreen(
            foodName = "",
            totalPrice = 0,
            navigateToHome = {}
        )
    }
}