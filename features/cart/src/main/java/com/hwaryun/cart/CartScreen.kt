package com.hwaryun.cart

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.hwaryun.common.ext.toNumberFormat
import com.hwaryun.designsystem.R
import com.hwaryun.designsystem.components.AsphaltAppBar
import com.hwaryun.designsystem.components.AsphaltCounterButton
import com.hwaryun.designsystem.components.PaymentSummary
import com.hwaryun.designsystem.components.atoms.AsphaltButton
import com.hwaryun.designsystem.components.atoms.AsphaltText
import com.hwaryun.designsystem.ui.FoodMarketTheme
import com.hwaryun.designsystem.ui.asphalt.AsphaltTheme
import com.hwaryun.domain.model.Cart

@Composable
internal fun CartRoute(
    popBackStack: () -> Unit,
    navigateToOrder: () -> Unit,
    navigateToSuccessOrder: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    viewModel: CartViewModel = hiltViewModel()
) {
    val cartState by viewModel.cartState.collectAsStateWithLifecycle()
    val transactionState by viewModel.transactionState.collectAsStateWithLifecycle()

    CartScreen(
        cartState = cartState,
        transactionState = transactionState,
        popBackStack = popBackStack,
        navigateToOrder = navigateToOrder,
        navigateToSuccessOrder = navigateToSuccessOrder,
        clearCart = viewModel::clearCart,
        addQuantity = viewModel::addQuantity,
        reduceQuantity = viewModel::reduceQuantity,
        onShowSnackbar = onShowSnackbar,
        onCheckoutClick = viewModel::checkout,
    )
}

@Composable
fun CartScreen(
    cartState: CartState,
    transactionState: TransactionState,
    popBackStack: () -> Unit,
    navigateToOrder: () -> Unit,
    navigateToSuccessOrder: () -> Unit,
    clearCart: () -> Unit,
    addQuantity: (Int) -> Unit,
    reduceQuantity: (Int) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    onCheckoutClick: () -> Unit,
) {
    val context = LocalContext.current

    LaunchedEffect(transactionState) {
        if (transactionState.transaction != null) {
            val intent = Intent()
                .setAction(Intent.ACTION_VIEW)
                .setData(Uri.parse(transactionState.transaction.paymentUrl))

            context.startActivity(intent)
            navigateToSuccessOrder()
        }

        if (transactionState.isCancelled) {
            navigateToOrder()
        }

        if (transactionState.error.isNotEmpty()) {
            onShowSnackbar(transactionState.error, null)
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
            cartState.cart?.let {
                Column(
                    modifier = Modifier.padding(
                        start = 24.dp,
                        end = 24.dp,
                        bottom = 24.dp
                    )
                ) {
                    AsphaltButton(
                        enabled = !transactionState.isLoading,
                        isLoading = transactionState.isLoading,
                        onClick = { onCheckoutClick() }
                    ) {
                        AsphaltText(text = "Pesan dan antar sekarang")
                    }
                }
            }
        },
        containerColor = AsphaltTheme.colors.pure_white_500
    ) { innerPadding ->
        if (cartState.cart == null) {
            EmptyState()
        } else {
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
                AddressSection(address = cartState.user?.address.orDash())

                DividerSection()

                FoodItemSection(
                    cart = cartState.cart,
                    qty = cartState.qty,
                    clearCart = clearCart,
                    addQuantity = addQuantity,
                    reduceQuantity = reduceQuantity
                )

                DividerSection()

                PaymentSummary(
                    totalFoodPrice = cartState.totalFoodPrice,
                    shippingCost = cartState.shippingCost,
                    serviceFee = cartState.serviceFee,
                    totalPrice = cartState.totalPrice,
                )
            }
        }
    }
}

@Composable
private fun EmptyState() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_order_empty),
            contentDescription = null,
            modifier = Modifier.size(180.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))
        AsphaltText(
            text = "Kamu belum menambahkan apapun ke keranjang",
            modifier = Modifier.fillMaxWidth(),
            style = AsphaltTheme.typography.titleModerateDemi,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun AddressSection(address: String) {
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
                    visible = false,
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
    cart: Cart?,
    qty: Int,
    clearCart: () -> Unit,
    addQuantity: (Int) -> Unit,
    reduceQuantity: (Int) -> Unit,
) {
    val context = LocalContext.current
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(cart?.picturePath)
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
                    visible = false,
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
                text = cart?.name.orDash(),
                modifier = Modifier
                    .fillMaxWidth()
                    .placeholder(
                        visible = false,
                        highlight = PlaceholderHighlight.shimmer(),
                        color = PlaceholderDefaults.color(),
                        shape = AsphaltTheme.shapes.medium
                    ),
                style = AsphaltTheme.typography.titleModerateDemi,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(12.dp))
            AsphaltCounterButton(
                value = qty,
                onDecrementClick = {
                    if (it <= 1) {
                        clearCart()
                        return@AsphaltCounterButton
                    }
                    reduceQuantity(it)

                },
                onIncrementClick = { addQuantity(it) })
        }

        Spacer(modifier = Modifier.width(16.dp))
        AsphaltText(
            text = cart?.price.toNumberFormat(),
            style = AsphaltTheme.typography.titleModerateDemi,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun PaymentSummarySection(
    totalFoodPrice: Int,
    shippingCost: Int,
    serviceFee: Int,
    totalPrice: Int,
) {
    Column {
        AsphaltText(
            text = "Ringkasan pembayaran",
            modifier = Modifier.fillMaxWidth(),
            style = AsphaltTheme.typography.titleSmallBold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AsphaltText(
                text = "Harga",
                modifier = Modifier.weight(1f),
                style = AsphaltTheme.typography.bodySmall
            )
            AsphaltText(
                text = totalFoodPrice.toNumberFormat(),
                modifier = Modifier.weight(1f),
                style = AsphaltTheme.typography.bodySmall,
                textAlign = TextAlign.End
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AsphaltText(
                text = "Ongkir",
                modifier = Modifier.weight(1f),
                style = AsphaltTheme.typography.bodySmall
            )
            AsphaltText(
                text = shippingCost.toNumberFormat(),
                modifier = Modifier.weight(1f),
                style = AsphaltTheme.typography.bodySmall,
                textAlign = TextAlign.End
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AsphaltText(
                text = "Biaya layanan & lainnya",
                modifier = Modifier.weight(1f),
                style = AsphaltTheme.typography.bodySmall
            )
            AsphaltText(
                text = serviceFee.toNumberFormat(),
                modifier = Modifier.weight(1f),
                style = AsphaltTheme.typography.bodySmall,
                textAlign = TextAlign.End
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Divider(color = AsphaltTheme.colors.cool_gray_1cCp_100)
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AsphaltText(
                text = "Total pembayaran",
                modifier = Modifier.weight(1f),
                style = AsphaltTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold)
            )
            AsphaltText(
                text = totalPrice.toNumberFormat(),
                modifier = Modifier.weight(1f),
                style = AsphaltTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.End
            )
        }
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
        CartScreen(
            cartState = CartState(),
            transactionState = TransactionState(),
            popBackStack = {},
            navigateToOrder = {},
            navigateToSuccessOrder = {},
            clearCart = {},
            addQuantity = {},
            reduceQuantity = {},
            onShowSnackbar = { _, _ -> true },
            onCheckoutClick = {},
        )
    }
}