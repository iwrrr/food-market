package com.hwaryun.order

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hwaryun.designsystem.R
import com.hwaryun.designsystem.components.FoodMarketButton
import com.hwaryun.designsystem.ui.FoodMarketTheme
import com.hwaryun.order.components.OrderItem

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun OrderItemScreen(onOrderItemClick: () -> Unit) {
    val listState = rememberLazyListState()
    val itemsList = (0..35).toList()

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 800.dp)
            .background(MaterialTheme.colorScheme.surface),
        contentPadding = PaddingValues(top = 8.dp, bottom = 80.dp),
        state = listState,
    ) {
        items(items = itemsList, key = { item -> item.hashCode() }) {
            OrderItem(
                onOrderItemClick = onOrderItemClick
            )
        }
    }
}

@Composable
private fun EmptyContent() {
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
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DefaultPreview() {
    FoodMarketTheme {
        OrderItemScreen(onOrderItemClick = {})
    }
}