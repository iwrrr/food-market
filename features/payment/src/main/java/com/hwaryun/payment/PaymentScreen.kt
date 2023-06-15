package com.hwaryun.payment

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.placeholder.PlaceholderDefaults
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.color
import com.google.accompanist.placeholder.material.shimmer
import com.google.accompanist.placeholder.placeholder
import com.hwaryun.designsystem.R
import com.hwaryun.designsystem.components.ButtonType
import com.hwaryun.designsystem.components.DialogBoxLoading
import com.hwaryun.designsystem.components.FoodMarketButton
import com.hwaryun.designsystem.components.FoodMarketTopAppBar
import com.hwaryun.designsystem.ui.FoodMarketTheme
import com.hwaryun.designsystem.ui.LightGreen
import com.hwaryun.designsystem.utils.convertUnixToDate
import com.hwaryun.designsystem.utils.toNumberFormat

@Composable
internal fun PaymentRoute(
    popBackStack: () -> Unit,
    navigateToOrder: () -> Unit,
    navigateToSuccessOrder: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    viewModel: PaymentViewModel = hiltViewModel()
) {
    val paymentUiState by viewModel.paymentUiState.collectAsStateWithLifecycle()
    val transactionUiState by viewModel.transactionUiState.collectAsStateWithLifecycle()

    PaymentScreen(
        paymentUiState = paymentUiState,
        transactionUiState = transactionUiState,
        popBackStack = popBackStack,
        navigateToOrder = navigateToOrder,
        navigateToSuccessOrder = navigateToSuccessOrder,
        onShowSnackbar = onShowSnackbar,
        onCheckoutClick = viewModel::checkout,
        onCancelOrderClick = viewModel::cancelOrder,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen(
    paymentUiState: PaymentUiState,
    transactionUiState: TransactionUiState,
    popBackStack: () -> Unit,
    navigateToOrder: () -> Unit,
    navigateToSuccessOrder: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    onCheckoutClick: () -> Unit,
    onCancelOrderClick: () -> Unit,
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    LaunchedEffect(transactionUiState) {
        if (transactionUiState.transaction != null) {
            val intent = Intent()
                .setAction(Intent.ACTION_VIEW)
                .setData(Uri.parse(transactionUiState.transaction.paymentUrl))

            context.startActivity(intent)
            navigateToSuccessOrder()
        }

        if (transactionUiState.isCancelled) {
            navigateToOrder()
        }

        if (transactionUiState.error.isNotEmpty()) {
            onShowSnackbar(transactionUiState.error, null)
        }
    }

    Scaffold(
        topBar = {
            FoodMarketTopAppBar(
                title = "Payment",
                subtitle = "You deserve better meal",
                showNavigateBack = true,
                onNavigateBack = { popBackStack() }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding() + 24.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(24.dp)
                ) {
                    Text(text = "Item Ordered")
                    Spacer(modifier = Modifier.height(12.dp))
                    Column(
                        verticalArrangement = Arrangement.Center
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(paymentUiState.food?.picturePath)
                                    .crossfade(true)
                                    .build(),
                                placeholder = painterResource(R.drawable.ic_placeholder),
                                error = painterResource(R.drawable.ic_placeholder),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(60.dp)
                                    .placeholder(
                                        visible = paymentUiState.isLoading,
                                        highlight = PlaceholderHighlight.shimmer(),
                                        color = PlaceholderDefaults.color(),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .clip(RoundedCornerShape(8.dp))
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "${paymentUiState.food?.name}",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .placeholder(
                                            visible = paymentUiState.isLoading,
                                            highlight = PlaceholderHighlight.shimmer(),
                                            color = PlaceholderDefaults.color(),
                                            shape = RoundedCornerShape(8.dp)
                                        ),
                                    style = MaterialTheme.typography.bodyLarge,
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = 1
                                )
                                Text(
                                    text = "IDR ${paymentUiState.food?.price.toNumberFormat()}",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .placeholder(
                                            visible = paymentUiState.isLoading,
                                            highlight = PlaceholderHighlight.shimmer(),
                                            color = PlaceholderDefaults.color(),
                                            shape = RoundedCornerShape(8.dp)
                                        ),
                                    style = MaterialTheme.typography.bodySmall,
                                    overflow = TextOverflow.Ellipsis,
                                    color = MaterialTheme.colorScheme.secondary,
                                    maxLines = 1
                                )
                            }
                            Text(
                                text = "${paymentUiState.qty} items",
                                modifier = Modifier
                                    .placeholder(
                                        visible = paymentUiState.isLoading,
                                        highlight = PlaceholderHighlight.shimmer(),
                                        color = PlaceholderDefaults.color(),
                                        shape = RoundedCornerShape(8.dp)
                                    ),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.secondary,
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Details Transaction")
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${paymentUiState.food?.name}",
                            modifier = Modifier
                                .weight(1f)
                                .placeholder(
                                    visible = paymentUiState.isLoading,
                                    highlight = PlaceholderHighlight.shimmer(),
                                    color = PlaceholderDefaults.color(),
                                    shape = RoundedCornerShape(8.dp)
                                ),
                            color = MaterialTheme.colorScheme.secondary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "IDR ${paymentUiState.totalFoodPrice}",
                            modifier = Modifier
                                .weight(1f)
                                .placeholder(
                                    visible = paymentUiState.isLoading,
                                    highlight = PlaceholderHighlight.shimmer(),
                                    color = PlaceholderDefaults.color(),
                                    shape = RoundedCornerShape(8.dp)
                                ),
                            textAlign = TextAlign.End,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Driver",
                            modifier = Modifier.weight(1f),
                            color = MaterialTheme.colorScheme.secondary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "IDR ${paymentUiState.shippingCost.toNumberFormat()}",
                            modifier = Modifier
                                .weight(1f)
                                .placeholder(
                                    visible = paymentUiState.isLoading,
                                    highlight = PlaceholderHighlight.shimmer(),
                                    color = PlaceholderDefaults.color(),
                                    shape = RoundedCornerShape(8.dp)
                                ),
                            textAlign = TextAlign.End,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Tax 10%",
                            modifier = Modifier.weight(1f),
                            color = MaterialTheme.colorScheme.secondary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "IDR ${paymentUiState.tax.toNumberFormat()}",
                            modifier = Modifier
                                .weight(1f)
                                .placeholder(
                                    visible = paymentUiState.isLoading,
                                    highlight = PlaceholderHighlight.shimmer(),
                                    color = PlaceholderDefaults.color(),
                                    shape = RoundedCornerShape(8.dp)
                                ),
                            textAlign = TextAlign.End,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Total Price",
                            modifier = Modifier.weight(1f),
                            color = MaterialTheme.colorScheme.secondary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "IDR ${paymentUiState.totalPrice.toNumberFormat()}",
                            modifier = Modifier
                                .weight(1f)
                                .placeholder(
                                    visible = paymentUiState.isLoading,
                                    highlight = PlaceholderHighlight.shimmer(),
                                    color = PlaceholderDefaults.color(),
                                    shape = RoundedCornerShape(8.dp)
                                ),
                            textAlign = TextAlign.End,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = LightGreen
                        )
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(24.dp)
                ) {
                    Text(text = "Deliver to:")
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Name",
                            modifier = Modifier.weight(1f),
                            color = MaterialTheme.colorScheme.secondary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                        Text(
                            text = "${paymentUiState.user?.name}",
                            modifier = Modifier
                                .weight(1f)
                                .placeholder(
                                    visible = paymentUiState.isLoading,
                                    highlight = PlaceholderHighlight.shimmer(),
                                    color = PlaceholderDefaults.color(),
                                    shape = RoundedCornerShape(8.dp)
                                ),
                            textAlign = TextAlign.End,
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Phone No.",
                            modifier = Modifier.weight(1f),
                            color = MaterialTheme.colorScheme.secondary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "${paymentUiState.user?.phoneNumber}",
                            modifier = Modifier
                                .weight(1f)
                                .placeholder(
                                    visible = paymentUiState.isLoading,
                                    highlight = PlaceholderHighlight.shimmer(),
                                    color = PlaceholderDefaults.color(),
                                    shape = RoundedCornerShape(8.dp)
                                ),
                            textAlign = TextAlign.End,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Address",
                            modifier = Modifier.weight(1f),
                            color = MaterialTheme.colorScheme.secondary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "${paymentUiState.user?.address}",
                            modifier = Modifier
                                .weight(1f)
                                .placeholder(
                                    visible = paymentUiState.isLoading,
                                    highlight = PlaceholderHighlight.shimmer(),
                                    color = PlaceholderDefaults.color(),
                                    shape = RoundedCornerShape(8.dp)
                                ),
                            textAlign = TextAlign.End,
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "House No.",
                            modifier = Modifier.weight(1f),
                            color = MaterialTheme.colorScheme.secondary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "${paymentUiState.user?.houseNumber}",
                            modifier = Modifier
                                .weight(1f)
                                .placeholder(
                                    visible = paymentUiState.isLoading,
                                    highlight = PlaceholderHighlight.shimmer(),
                                    color = PlaceholderDefaults.color(),
                                    shape = RoundedCornerShape(8.dp)
                                ),
                            textAlign = TextAlign.End,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "City",
                            modifier = Modifier.weight(1f),
                            color = MaterialTheme.colorScheme.secondary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "${paymentUiState.user?.city}",
                            modifier = Modifier
                                .weight(1f)
                                .placeholder(
                                    visible = paymentUiState.isLoading,
                                    highlight = PlaceholderHighlight.shimmer(),
                                    color = PlaceholderDefaults.color(),
                                    shape = RoundedCornerShape(8.dp)
                                ),
                            textAlign = TextAlign.End,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
                if (paymentUiState.transaction != null) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(24.dp)
                    ) {
                        Text(text = "Order Status:")
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            val transactionCode =
                                paymentUiState.transaction.updatedAt.convertUnixToDate("yyMM")
                            Text(
                                text = "#FM" + transactionCode + paymentUiState.transaction.id,
                                modifier = Modifier.weight(1f),
                                color = MaterialTheme.colorScheme.secondary,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                            Text(
                                text = paymentUiState.transaction.status,
                                modifier = Modifier.weight(1f),
                                color = LightGreen,
                                textAlign = TextAlign.End,
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                if (!paymentUiState.isLoading) {
                    Column(
                        modifier = Modifier.padding(24.dp)
                    ) {
                        if (paymentUiState.transaction == null) {
                            FoodMarketButton(
                                text = "Checkout Now",
                                modifier = Modifier.fillMaxWidth(),
                                onClick = { onCheckoutClick() },
                            )
                        } else {
                            if (paymentUiState.transaction.status == "On Delivery") {
                                FoodMarketButton(
                                    text = "Cancel My Order",
                                    modifier = Modifier.fillMaxWidth(),
                                    type = ButtonType.Error,
                                    onClick = { onCancelOrderClick() },
                                )
                            }
                        }
                    }
                }
            }

            if (transactionUiState.isLoading) {
                DialogBoxLoading()
            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DefaultPreview() {
    FoodMarketTheme {
        PaymentScreen(
            paymentUiState = PaymentUiState(),
            transactionUiState = TransactionUiState(),
            popBackStack = {},
            navigateToOrder = {},
            navigateToSuccessOrder = {},
            onShowSnackbar = { _, _ -> true },
            onCheckoutClick = {},
            onCancelOrderClick = {}
        )
    }
}