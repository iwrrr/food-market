package com.hwaryun.transaction

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.hwaryun.datasource.paging.subscribe
import com.hwaryun.designsystem.R
import com.hwaryun.designsystem.components.atoms.AsphaltText
import com.hwaryun.designsystem.components.molecules.AsphaltAppBar
import com.hwaryun.designsystem.screen.NoConnectionScreen
import com.hwaryun.designsystem.ui.FoodMarketTheme
import com.hwaryun.designsystem.ui.asphalt.AsphaltTheme
import com.hwaryun.domain.model.Transaction
import com.hwaryun.transaction.components.FilterStatus
import com.hwaryun.transaction.components.PlaceholderTransactionItem
import com.hwaryun.transaction.components.TransactionItem
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
internal fun TransactionRoute(
    onTransactionClick: (Int) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    viewModel: TransactionViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val transactions = viewModel.transactions.collectAsLazyPagingItems()

    TransactionScreen(
        state = state,
        transactions = transactions,
        onTransactionClick = onTransactionClick,
        onShowSnackbar = onShowSnackbar,
        filterAll = viewModel::filterAll,
        filterOnDelivery = viewModel::filterOnDelivery,
        filterDelivered = viewModel::filterDelivered,
        filterCancelled = viewModel::filterCancelled,
        resetErrorState = viewModel::resetErrorState,
    )
}

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TransactionScreen(
    state: TransactionState,
    transactions: LazyPagingItems<Transaction>,
    onTransactionClick: (Int) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    filterAll: () -> Unit,
    filterOnDelivery: () -> Unit,
    filterDelivered: () -> Unit,
    filterCancelled: () -> Unit,
    resetErrorState: () -> Unit,
) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = false,
        onRefresh = transactions::refresh
    )

    val shouldShowEmptyScreen = transactions.itemSnapshotList.isEmpty()
    val shouldShowErrorScreen = transactions.loadState.refresh is LoadState.Error
    val finishedLoading =
        transactions.loadState.refresh !is LoadState.Loading &&
                transactions.loadState.prepend !is LoadState.Loading &&
                transactions.loadState.append !is LoadState.Loading

    LaunchedEffect(state) {
        if (state.error.isNotEmpty()) {
            onShowSnackbar(state.error, null)
            resetErrorState()
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        containerColor = AsphaltTheme.colors.cool_gray_1cCp_100,
        topBar = {
            AsphaltAppBar(
                title = stringResource(id = R.string.title_history),
                subtitle = stringResource(id = R.string.subtitle_history)
            ) {
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
                Divider(thickness = 1.dp, color = AsphaltTheme.colors.cool_gray_1cCp_100)
            }
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier.pullRefresh(pullRefreshState),
                contentAlignment = Alignment.TopCenter
            ) {
                when {
                    shouldShowErrorScreen -> {
                        NoConnectionScreen(onTryAgain = transactions::refresh)
                    }

                    shouldShowEmptyScreen && finishedLoading -> {
                        EmptyContent()
                    }

                    else -> {
                        LazyColumn(
                            modifier = Modifier.padding(innerPadding),
                            contentPadding = PaddingValues(top = 12.dp, bottom = 20.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
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
                            )
                        }
                    }
                }

                PullRefreshIndicator(
                    refreshing = false,
                    state = pullRefreshState,
                    contentColor = AsphaltTheme.colors.gojek_green_500
                )
            }
        }
    )
}

@Composable
private fun EmptyContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
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
        AsphaltText(
            text = "Oops, nggak ada transaksi yang sesuai filter",
            style = AsphaltTheme.typography.titleLarge,
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
        TransactionScreen(
            state = TransactionState(),
            transactions = flow.collectAsLazyPagingItems(),
            onShowSnackbar = { _, _ -> false },
            onTransactionClick = {},
            filterAll = {},
            filterOnDelivery = {},
            filterDelivered = {},
            filterCancelled = {},
            resetErrorState = {},
        )
    }
}