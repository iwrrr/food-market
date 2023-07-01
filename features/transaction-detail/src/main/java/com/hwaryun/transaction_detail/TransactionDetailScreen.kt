package com.hwaryun.transaction_detail

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
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
import com.hwaryun.common.ext.orDash
import com.hwaryun.common.ext.orZero
import com.hwaryun.common.ext.toNumberFormat
import com.hwaryun.designsystem.R
import com.hwaryun.designsystem.components.atoms.AsphaltButton
import com.hwaryun.designsystem.components.atoms.AsphaltText
import com.hwaryun.designsystem.components.atoms.ButtonType
import com.hwaryun.designsystem.components.molecules.AsphaltAppBar
import com.hwaryun.designsystem.components.organisms.PaymentSummary
import com.hwaryun.designsystem.ui.FoodMarketTheme
import com.hwaryun.designsystem.ui.asphalt.AsphaltTheme
import com.hwaryun.domain.model.Transaction
import com.hwaryun.transaction_detail.utils.CANCELLED

@Composable
internal fun TransactionDetailRoute(
    popBackStack: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    viewModel: TransactionDetailViewModel = hiltViewModel()
) {
    val transactionState by viewModel.transactionState.collectAsStateWithLifecycle()
    val cancelOrderState by viewModel.cancelOrderState.collectAsStateWithLifecycle()

    TransactionDetailScreen(
        transactionState = transactionState,
        cancelOrderState = cancelOrderState,
        popBackStack = popBackStack,
        onShowSnackbar = onShowSnackbar,
        onCancelOrderClick = viewModel::cancelOrder,
        resetErrorState = viewModel::resetErrorState,
    )
}

@Composable
fun TransactionDetailScreen(
    transactionState: TransactionState,
    cancelOrderState: CancelOrderState,
    popBackStack: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    onCancelOrderClick: () -> Unit,
    resetErrorState: () -> Unit,
) {
    LaunchedEffect(transactionState, cancelOrderState) {
        if (cancelOrderState.isCancelled) {
            popBackStack()
        }

        if (transactionState.error.isNotEmpty()) {
            onShowSnackbar(transactionState.error, null)
            resetErrorState()
        }

        if (cancelOrderState.error.isNotEmpty()) {
            onShowSnackbar(cancelOrderState.error, null)
            resetErrorState()
        }
    }

    Scaffold(
        modifier = Modifier.statusBarsPadding(),
        topBar = {
            AsphaltAppBar(
                title = "Keranjang",
                showNavigateBack = true,
                onNavigateBack = { popBackStack() }
            )
        },
        bottomBar = {
            transactionState.transaction?.let {
                val isCancelled = it.status == CANCELLED
                val textButton = if (isCancelled) "Pesanan dibatalkan" else "Batalkan pesanan"

                Column(
                    modifier = Modifier.padding(
                        start = 24.dp,
                        end = 24.dp,
                        bottom = 32.dp
                    )
                ) {
                    AsphaltButton(
                        enabled = !cancelOrderState.isLoading && !isCancelled,
                        isLoading = cancelOrderState.isLoading,
                        type = ButtonType.Outline,
                        color = AsphaltTheme.colors.retail_red_500,
                        onClick = { onCancelOrderClick() }
                    ) {
                        AsphaltText(text = textButton)
                    }
                }
            }
        },
        containerColor = AsphaltTheme.colors.pure_white_500
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = innerPadding.calculateTopPadding() + 24.dp,
                    start = 24.dp,
                    end = 24.dp,
                    bottom = innerPadding.calculateTopPadding() + 16.dp
                )
        ) {
            AddressSection(
                address = transactionState.transaction?.user?.address.orDash(),
                isLoading = transactionState.isLoading
            )

            DividerSection()

            FoodItemSection(
                transaction = transactionState.transaction,
                isLoading = transactionState.isLoading
            )

            DividerSection()

            PaymentSummary(
                totalFoodPrice = transactionState.transaction?.food?.price.orZero() * transactionState.transaction?.quantity.orZero(),
                totalPrice = transactionState.transaction?.total.orZero(),
                isLoading = transactionState.isLoading
            )
        }
    }
}

@Composable
private fun AddressSection(
    address: String,
    isLoading: Boolean
) {
    Column {
        AsphaltText(
            text = "Alamat pengiriman",
            style = AsphaltTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
            color = AsphaltTheme.colors.sub_black_500
        )
        Spacer(modifier = Modifier.height(8.dp))
        AsphaltText(
            text = "Rumah",
            modifier = Modifier.fillMaxWidth(),
            style = AsphaltTheme.typography.titleModerateDemi
        )
        Spacer(modifier = Modifier.height(8.dp))
        AsphaltText(
            text = address,
            modifier = Modifier
                .fillMaxWidth()
                .placeholder(
                    visible = isLoading,
                    highlight = PlaceholderHighlight.shimmer(),
                    color = PlaceholderDefaults.color(),
                    shape = AsphaltTheme.shapes.medium
                ),
            style = AsphaltTheme.typography.captionModerateBook
        )
    }
}

@Composable
private fun FoodItemSection(
    transaction: Transaction?,
    isLoading: Boolean
) {
    val context = LocalContext.current
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(transaction?.food?.picturePath)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.ic_placeholder),
            error = painterResource(R.drawable.ic_placeholder),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(100.dp)
                .aspectRatio(3f / 4f)
                .placeholder(
                    visible = isLoading,
                    highlight = PlaceholderHighlight.shimmer(),
                    color = PlaceholderDefaults.color(),
                    shape = RoundedCornerShape(8.dp)
                )
                .clip(AsphaltTheme.shapes.small)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            AsphaltText(
                text = transaction?.food?.name.orDash(),
                modifier = Modifier
                    .fillMaxWidth()
                    .placeholder(
                        visible = isLoading,
                        highlight = PlaceholderHighlight.shimmer(),
                        color = PlaceholderDefaults.color(),
                        shape = AsphaltTheme.shapes.medium
                    ),
                style = AsphaltTheme.typography.titleModerateDemi,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }

        Spacer(modifier = Modifier.width(16.dp))
        AsphaltText(
            text = transaction?.food?.price.toNumberFormat(),
            modifier = Modifier
                .defaultMinSize(minWidth = 100.dp)
                .placeholder(
                    visible = isLoading,
                    highlight = PlaceholderHighlight.shimmer(),
                    color = PlaceholderDefaults.color(),
                    shape = AsphaltTheme.shapes.medium
                ),
            style = AsphaltTheme.typography.titleModerateDemi,
            textAlign = TextAlign.End,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun DividerSection() {
    Spacer(modifier = Modifier.height(16.dp))
    Divider(color = AsphaltTheme.colors.cool_gray_1cCp_100)
    Spacer(modifier = Modifier.height(16.dp))
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DefaultPreview() {
    FoodMarketTheme {
        TransactionDetailScreen(
            transactionState = TransactionState(),
            cancelOrderState = CancelOrderState(),
            popBackStack = {},
            onShowSnackbar = { _, _ -> true },
            onCancelOrderClick = {},
            resetErrorState = {},
        )
    }
}