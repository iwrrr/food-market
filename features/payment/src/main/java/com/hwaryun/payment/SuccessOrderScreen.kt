package com.hwaryun.payment

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hwaryun.designsystem.R
import com.hwaryun.designsystem.components.FoodMarketButton
import com.hwaryun.designsystem.ui.FoodMarketTheme
import com.hwaryun.designsystem.utils.ButtonType

@Composable
internal fun SuccessOrderRoute(
    navigateToHome: () -> Unit,
    navigateToOrder: () -> Unit,
) {
    SuccessOrderScreen(
        navigateToHome = navigateToHome,
        navigateToOrder = navigateToOrder,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun SuccessOrderScreen(
    navigateToHome: () -> Unit,
    navigateToOrder: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_order_success),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2f)
        )
        Spacer(modifier = Modifier.height(28.dp))
        Text(
            text = "You’ve Made Order",
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Just stay at home while we are\npreparing your best foods",
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(28.dp))
        Column(
            modifier = Modifier.padding(horizontal = 80.dp),
        ) {
            FoodMarketButton(
                text = "Order Other Foods",
                modifier = Modifier.fillMaxWidth(),
                onClick = { navigateToHome() },
            )
            Spacer(modifier = Modifier.height(12.dp))
            FoodMarketButton(
                text = "View My Order",
                modifier = Modifier.fillMaxWidth(),
                type = ButtonType.Outline,
                onClick = { navigateToOrder() },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    FoodMarketTheme {
        SuccessOrderScreen(
            navigateToHome = {},
            navigateToOrder = {}
        )
    }
}

@Preview(showBackground = true, widthDp = 480)
@Composable
private fun PortraitPreview() {
    FoodMarketTheme {
        SuccessOrderScreen(
            navigateToHome = {},
            navigateToOrder = {}
        )
    }
}