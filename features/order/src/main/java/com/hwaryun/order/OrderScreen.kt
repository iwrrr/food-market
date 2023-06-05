package com.hwaryun.order

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hwaryun.designsystem.R
import com.hwaryun.designsystem.component.FoodMarketButton
import com.hwaryun.designsystem.component.FoodMarketTopAppBar
import com.hwaryun.designsystem.ui.FoodMarketTheme

@Composable
internal fun OrderRoute(onOrderClick: () -> Unit) {
    OrderScreen(onOrderClick = onOrderClick)
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun OrderScreen(onOrderClick: () -> Unit) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (true) {

            } else {
                FoodMarketTopAppBar(title = "Your Orders", subtitle = "Wait for the best meal")
            }
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (true) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_order_empty),
                        contentDescription = null,
                        modifier = Modifier
                            .width(200.dp)
                            .height(220.dp)
                    )
                    Spacer(modifier = Modifier.height(28.dp))
                    Text(
                        text = "Ouch! Hungry",
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Seems like you have not\n" +
                                "ordered any food yet",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(28.dp))
                    FoodMarketButton(
                        text = "Find Foods",
                        modifier = Modifier.fillMaxWidth(1f / 2),
                        onClick = { }
                    )
                } else {
                    Text(
                        text = "Orders",
                        fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    FoodMarketTheme {
        OrderScreen(onOrderClick = {})
    }
}

@Preview(showBackground = true, widthDp = 480)
@Composable
private fun PortraitPreview() {
    FoodMarketTheme {
        OrderScreen(onOrderClick = {})
    }
}