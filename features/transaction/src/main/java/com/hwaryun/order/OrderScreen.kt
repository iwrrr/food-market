package com.hwaryun.order

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.hwaryun.designsystem.R
import com.hwaryun.designsystem.components.AsphaltAppBar
import com.hwaryun.designsystem.components.FoodMarketButton
import com.hwaryun.designsystem.components.FoodMarketTabSection
import com.hwaryun.designsystem.components.TabItem
import com.hwaryun.designsystem.ui.Yellow
import com.hwaryun.domain.model.Transaction

@Composable
internal fun OrderRoute(
    navigateToHome: () -> Unit,
    onOrderClick: (Int) -> Unit,
    viewModel: OrderViewModel = hiltViewModel()
) {
    val inProgressOrders = viewModel.inProgressOrders.collectAsLazyPagingItems()
    val postOrders = viewModel.postOrders.collectAsLazyPagingItems()

    OrderScreen(
        navigateToHome = navigateToHome,
        onOrderClick = onOrderClick,
        inProgressOrders = inProgressOrders,
        postOrders = postOrders
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun OrderScreen(
    navigateToHome: () -> Unit,
    onOrderClick: (Int) -> Unit,
    inProgressOrders: LazyPagingItems<Transaction>,
    postOrders: LazyPagingItems<Transaction>
) {
    val shouldShowLoading =
        inProgressOrders.loadState.refresh is LoadState.Loading && postOrders.loadState.refresh is LoadState.Loading
    val shouldShowEmptyScreen =
        inProgressOrders.itemCount <= 0 && postOrders.itemCount <= 0

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (!shouldShowLoading) {
                if (!shouldShowEmptyScreen) {
                    AsphaltAppBar(title = "Your Orders", subtitle = "Wait for the best meal")
                }
            }
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = if (shouldShowEmptyScreen) Arrangement.Center else Arrangement.Top
            ) {
                when {
                    shouldShowLoading -> LoadingContent()
                    shouldShowEmptyScreen -> EmptyContent(navigateToHome)
                    else -> OrderContent(
                        onOrderClick = onOrderClick,
                        inProgressOrders = inProgressOrders,
                        postOrders = postOrders
                    )
                }
            }
        }
    )
}

@Composable
private fun OrderContent(
    onOrderClick: (Int) -> Unit,
    inProgressOrders: LazyPagingItems<Transaction>,
    postOrders: LazyPagingItems<Transaction>
) {
    Spacer(modifier = Modifier.height(24.dp))
    FoodMarketTabSection(
        tabItems = listOf(
            TabItem(
                title = "In Progress",
                screen = {
                    OrderItemScreen(
                        onOrderItemClick = onOrderClick,
                        orders = inProgressOrders
                    )
                }
            ),
            TabItem(
                title = "Past Orders",
                screen = {
                    OrderItemScreen(
                        onOrderItemClick = onOrderClick,
                        orders = postOrders
                    )
                }
            ),
        )
    )
}

@Composable
private fun EmptyContent(navigateToHome: () -> Unit) {
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
        onClick = { navigateToHome() }
    )
}

@Composable
private fun LoadingContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = Yellow)
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DefaultPreview() {
    //    FoodMarketTheme {
    //        OrderScreen(onOrderClick = {}, inProgressOrders = inProgressOrders, postOrders = postOrders)
    //    }
}