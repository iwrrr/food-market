package com.hwaryun.payment

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hwaryun.designsystem.R
import com.hwaryun.designsystem.components.atoms.AsphaltButton
import com.hwaryun.designsystem.components.atoms.AsphaltText
import com.hwaryun.designsystem.components.atoms.ButtonType
import com.hwaryun.designsystem.ui.FoodMarketTheme
import com.hwaryun.designsystem.ui.asphalt.AsphaltTheme

@Composable
internal fun SuccessOrderRoute(
    navigateToHome: () -> Unit,
) {
    SuccessOrderScreen(
        navigateToHome = navigateToHome,
    )
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun SuccessOrderScreen(
    navigateToHome: () -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = AsphaltTheme.colors.pure_white_500
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.weight(1f))
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
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            AsphaltText(
                text = "Kamu telah menyelesaikan pembayaran",
                modifier = Modifier.fillMaxWidth(),
                style = AsphaltTheme.typography.titleSmallDemi,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.weight(1f))
            Column(
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 24.dp),
            ) {
                AsphaltButton(
                    modifier = Modifier.fillMaxWidth(),
                    type = ButtonType.Primary,
                    onClick = { navigateToHome() },
                ) {
                    AsphaltText(text = "Selesai")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    FoodMarketTheme {
        SuccessOrderScreen {}
    }
}