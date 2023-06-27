package com.hwaryun.order

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.hwaryun.datasource.paging.subscribe
import com.hwaryun.designsystem.R
import com.hwaryun.designsystem.components.AsphaltAppBar
import com.hwaryun.designsystem.components.atoms.AsphaltText
import com.hwaryun.designsystem.ui.FoodMarketTheme
import com.hwaryun.designsystem.ui.asphalt.AsphaltTheme
import com.hwaryun.domain.model.Transaction
import com.hwaryun.order.components.FilterStatus
import com.hwaryun.order.components.PlaceholderTransactionItem
import com.hwaryun.order.components.TransactionItem
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
internal fun TransactionRoute(
    navigateToHome: () -> Unit,
    onTransactionClick: (Int) -> Unit,
    viewModel: TransactionViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val transactions = viewModel.transactions.collectAsLazyPagingItems()

    OrderScreen(
        state = state,
        transactions = transactions,
        navigateToHome = navigateToHome,
        onTransactionClick = onTransactionClick,
        filterAll = viewModel::filterAll,
        filterOnDelivery = viewModel::filterOnDelivery,
        filterDelivered = viewModel::filterDelivered,
        filterCancelled = viewModel::filterCancelled,
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun OrderScreen(
    state: TransactionState,
    transactions: LazyPagingItems<Transaction>,
    navigateToHome: () -> Unit,
    onTransactionClick: (Int) -> Unit,
    filterAll: () -> Unit,
    filterOnDelivery: () -> Unit,
    filterDelivered: () -> Unit,
    filterCancelled: () -> Unit,
) {
    val shouldShowEmptyScreen = transactions.itemCount <= 0

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = AsphaltTheme.colors.pure_white_500,
        topBar = {
            Column {
                AsphaltAppBar(title = "Riwayat")
                FilterStatus(
                    filterAllSelected = state.filterAllSelected,
                    filterOnDeliverySelected = state.filterOnDeliverySelected,
                    filterDeliveredSelected = state.filterDeliveredSelected,
                    filterCancelledSelected = state.filterCancelledSelected,
                    filterAll = filterAll,
                    filterOnDelivery = filterOnDelivery,
                    filterDelivered = filterDelivered,
                    filterCancelled = filterCancelled,
                )
            }
        },
        content = { innerPadding ->
            LazyColumn(
                modifier = Modifier.padding(innerPadding),
                contentPadding = PaddingValues(bottom = 64.dp)
            ) {
                transactions.loadState.refresh.subscribe(
                    doOnLoading = {
                        item {
                            repeat(10) {
                                PlaceholderTransactionItem()
                            }
                        }
                    },
                    doOnNotLoading = {
                        when {
                            shouldShowEmptyScreen -> {
                                item {
                                    EmptyContent()
                                }
                            }

                            else -> {
                                items(transactions, key = { it.id }) { transaction ->
                                    if (transaction != null) {
                                        TransactionItem(
                                            transaction = transaction,
                                            onTransactionClick = onTransactionClick
                                        )
                                    } else {
                                        PlaceholderTransactionItem()
                                    }
                                }
                            }
                        }
                    }
                )
            }
        }
    )
}

@Composable
private fun EmptyContent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(150.dp))
        Image(
            painter = painterResource(id = R.drawable.ic_order_empty),
            contentDescription = null,
            modifier = Modifier
                .width(200.dp)
                .height(220.dp)
        )
        Spacer(modifier = Modifier.height(28.dp))
        AsphaltText(
            text = "Oops, nggak ada transaksi yang sesuai filter",
            style = AsphaltTheme.typography.titleExtraLarge,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(8.dp))
        AsphaltText(
            text = "Coba ubah filter kamu, ya.",
            style = AsphaltTheme.typography.titleSmallDemi,
            textAlign = TextAlign.Center,
            color = AsphaltTheme.colors.cool_gray_500
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DefaultPreview() {
    val data = emptyList<Transaction>()
    val flow = MutableStateFlow(PagingData.from(data))
    FoodMarketTheme {
        OrderScreen(
            state = TransactionState(),
            transactions = flow.collectAsLazyPagingItems(),
            navigateToHome = {},
            onTransactionClick = {},
            filterAll = {},
            filterOnDelivery = {},
            filterDelivered = {},
            filterCancelled = {},
        )
    }
}