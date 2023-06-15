package com.hwaryun.order

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.hwaryun.datasource.paging.subscribe
import com.hwaryun.designsystem.R
import com.hwaryun.designsystem.ui.Yellow
import com.hwaryun.domain.model.Transaction
import com.hwaryun.order.components.OrderItem
import com.hwaryun.order.components.PlaceholderOrderItem

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun OrderItemScreen(
    onOrderItemClick: (Int) -> Unit,
    orders: LazyPagingItems<Transaction>
) {
    val pullRefreshState = rememberPullRefreshState(false, orders::refresh)
    val shouldShowEmptyScreen = orders.itemSnapshotList.isEmpty()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState),
        contentAlignment = Alignment.Center
    ) {

        when {
            shouldShowEmptyScreen -> EmptyContent()
            else -> OrderContent(onOrderItemClick = onOrderItemClick, orders = orders)
        }

        PullRefreshIndicator(
            refreshing = false,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter),
            contentColor = Yellow
        )
    }
}

@Composable
private fun EmptyContent() {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(MaterialTheme.colorScheme.surface),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
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
    }
}

@Composable
private fun OrderContent(
    onOrderItemClick: (Int) -> Unit,
    orders: LazyPagingItems<Transaction>
) {
    val listState = rememberLazyListState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        contentPadding = PaddingValues(top = 8.dp, bottom = 80.dp),
        state = listState,
    ) {
        orders.loadState.refresh.subscribe(
            doOnLoading = {
                item {
                    repeat(10) {
                        PlaceholderOrderItem()
                    }
                }
            },
            doOnNotLoading = {
                items(orders, key = { it.id }) { order ->
                    if (order != null) {
                        OrderItem(
                            order = order,
                            onOrderItemClick = onOrderItemClick
                        )
                    } else {
                        PlaceholderOrderItem()
                    }
                }
            },
        )

        orders.loadState.append.subscribe(
            doOnLoading = {
                item {
                    PlaceholderOrderItem()
                }
            }
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DefaultPreview() {
    //    FoodMarketTheme {
    //        OrderItemScreen(onOrderItemClick = {}, orders = inProgressOrders)
    //    }
}