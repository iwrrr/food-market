package com.hwaryun.designsystem.screen

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hwaryun.designsystem.R
import com.hwaryun.designsystem.components.atoms.AsphaltButton
import com.hwaryun.designsystem.components.atoms.AsphaltText
import com.hwaryun.designsystem.components.atoms.ButtonType
import com.hwaryun.designsystem.components.atoms.basic.Surface
import com.hwaryun.designsystem.ui.FoodMarketTheme
import com.hwaryun.designsystem.ui.asphalt.AsphaltTheme

@Composable
fun NoConnectionScreen(
    onTryAgain: (() -> Unit)? = null
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_no_connection),
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(12.dp))
            AsphaltText(
                text = "Tidak ada koneksi internet",
                style = AsphaltTheme.typography.titleTinyDemi
            )
            if (onTryAgain != null) {
                Spacer(modifier = Modifier.height(12.dp))
                AsphaltButton(
                    type = ButtonType.Outline,
                    onClick = onTryAgain
                ) {
                    AsphaltText(text = "Coba Lagi")
                }
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun DefaultPreview() {
    FoodMarketTheme {
        NoConnectionScreen()
    }
}