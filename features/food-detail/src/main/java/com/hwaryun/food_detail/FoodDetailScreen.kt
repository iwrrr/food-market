package com.hwaryun.food_detail

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarConfig
import com.hwaryun.common.ext.orDash
import com.hwaryun.common.ext.orZero
import com.hwaryun.common.ext.toNumberFormat
import com.hwaryun.designsystem.R
import com.hwaryun.designsystem.components.atoms.AsphaltButton
import com.hwaryun.designsystem.components.atoms.AsphaltText
import com.hwaryun.designsystem.components.molecules.AsphaltAppBar
import com.hwaryun.designsystem.components.molecules.FavoriteButton
import com.hwaryun.designsystem.screen.NoConnectionScreen
import com.hwaryun.designsystem.ui.FoodMarketTheme
import com.hwaryun.designsystem.ui.asphalt.AsphaltTheme
import com.hwaryun.domain.model.Food
import kotlinx.coroutines.launch

@Composable
internal fun FoodDetailRoute(
    popBackStack: () -> Unit,
    navigateToCart: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    viewModel: FoodDetailViewModel = hiltViewModel()
) {
    val foodDetailState by viewModel.foodDetailState.collectAsStateWithLifecycle()
    val wishlistState by viewModel.wishlistState.collectAsStateWithLifecycle()
    val cartState by viewModel.cartState.collectAsStateWithLifecycle()
    val isOffline by viewModel.isOffline.collectAsStateWithLifecycle()

    FoodDetailScreen(
        foodDetailState = foodDetailState,
        wishlistState = wishlistState,
        cartState = cartState,
        isOffline = isOffline,
        popBackStack = popBackStack,
        navigateToCart = navigateToCart,
        addToWishlist = viewModel::addToWishlist,
        removeWishlist = viewModel::removeWishlist,
        addToCart = viewModel::addToCart,
        refresh = viewModel::refresh,
        resetErrorState = viewModel::resetErrorState,
        onShowSnackbar = onShowSnackbar
    )
}

@Composable
fun FoodDetailScreen(
    foodDetailState: FoodDetailState,
    wishlistState: WishlistState,
    cartState: CartState,
    isOffline: Boolean,
    popBackStack: () -> Unit,
    navigateToCart: () -> Unit,
    addToWishlist: (Food?) -> Unit,
    removeWishlist: (Int?) -> Unit,
    addToCart: (Food) -> Unit,
    refresh: () -> Unit,
    resetErrorState: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(foodDetailState, cartState, wishlistState) {
        if (cartState.addToCart != null) {
            navigateToCart()
        }

        if (foodDetailState.error.isNotEmpty()) {
            onShowSnackbar(foodDetailState.error, null)
            resetErrorState()
        }

        if (cartState.error.isNotEmpty()) {
            onShowSnackbar(cartState.error, null)
            resetErrorState()
        }

        if (wishlistState.error.isNotEmpty()) {
            onShowSnackbar(wishlistState.error, null)
            resetErrorState()
        }
    }

    if (isOffline) {
        NoConnectionScreen()
    } else {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding(),
            containerColor = AsphaltTheme.colors.pure_white_500,
            topBar = {
                AsphaltAppBar(
                    title = stringResource(id = R.string.title_detail),
                    showNavigateBack = true,
                    onNavigateBack = popBackStack
                )
            },
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .padding(
                            top = it.calculateTopPadding() + 16.dp,
                            start = 24.dp,
                            end = 24.dp,
                            bottom = it.calculateBottomPadding() + 16.dp,
                        ),
                ) {
                    Box {
                        AsyncImage(
                            model = ImageRequest.Builder(context)
                                .data(foodDetailState.food?.picturePath)
                                .crossfade(true)
                                .build(),
                            placeholder = painterResource(R.drawable.ic_placeholder),
                            error = painterResource(R.drawable.ic_placeholder),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .placeholder(
                                    visible = foodDetailState.isLoading,
                                    highlight = PlaceholderHighlight.shimmer(),
                                    color = PlaceholderDefaults.color(),
                                    shape = AsphaltTheme.shapes.medium
                                )
                                .height(300.dp)
                                .clip(AsphaltTheme.shapes.medium)
                        )
                        if (foodDetailState.food?.types?.contains("popular") == true) {
                            AsphaltText(
                                text = "Trending",
                                modifier = Modifier
                                    .offset(x = 12.dp, y = 12.dp)
                                    .clip(AsphaltTheme.shapes.small)
                                    .background(AsphaltTheme.colors.pure_white_500)
                                    .padding(6.dp),
                                style = AsphaltTheme.typography.captionSmallBook
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsphaltText(
                            text = foodDetailState.food?.name.orDash(),
                            modifier = Modifier
                                .weight(1f)
                                .placeholder(
                                    visible = foodDetailState.isLoading,
                                    highlight = PlaceholderHighlight.shimmer(),
                                    color = PlaceholderDefaults.color(),
                                    shape = RoundedCornerShape(8.dp)
                                ),
                            style = AsphaltTheme.typography.titleLarge,
                        )

                        FavoriteButton(
                            isChecked = wishlistState.isWishlist,
                            onClick = {
                                if (wishlistState.isWishlist) {
                                    removeWishlist(foodDetailState.food?.id)
                                    scope.launch {
                                        onShowSnackbar(
                                            "Berhasil menghapus makanan dari wishlist",
                                            null
                                        )
                                    }

                                } else {
                                    addToWishlist(foodDetailState.food)
                                    scope.launch {
                                        onShowSnackbar(
                                            "Berhasil menambahkan makanan ke wishlist",
                                            null
                                        )
                                    }
                                }
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RatingBar(
                            value = 1f,
                            config = RatingBarConfig()
                                .isIndicator(true)
                                .size(12.dp)
                                .numStars(1),
                            onValueChange = {},
                            onRatingChanged = {}
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        AsphaltText(
                            text = "${foodDetailState.food?.rate.orZero()}",
                            modifier = Modifier
                                .placeholder(
                                    visible = foodDetailState.isLoading,
                                    highlight = PlaceholderHighlight.shimmer(),
                                    color = PlaceholderDefaults.color(),
                                    shape = RoundedCornerShape(8.dp)
                                ),
                            style = AsphaltTheme.typography.captionSmallDemi
                        )
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    AsphaltText(
                        text = foodDetailState.food?.ingredients.orDash(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .placeholder(
                                visible = foodDetailState.isLoading,
                                highlight = PlaceholderHighlight.shimmer(),
                                color = PlaceholderDefaults.color(),
                                shape = RoundedCornerShape(8.dp)
                            ),
                        style = AsphaltTheme.typography.bodySmall
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsphaltText(
                            text = "Rp ${foodDetailState.food?.price.toNumberFormat()}",
                            modifier = Modifier.weight(1f),
                            style = AsphaltTheme.typography.titleExtraLarge.copy(
                                fontWeight = FontWeight.Medium
                            ),
                            textAlign = TextAlign.Center
                        )
                        AsphaltButton(
                            modifier = Modifier.weight(1f),
                            enabled = !cartState.isLoading,
                            isLoading = cartState.isLoading,
                            onClick = {
                                foodDetailState.food?.let(addToCart)
                            }
                        ) {
                            AsphaltText(text = "Tambah ke Keranjang")
                        }
                    }
                }
            }
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun FoodDetailScreenPreview() {
    FoodMarketTheme {
        FoodDetailScreen(
            foodDetailState = FoodDetailState(),
            wishlistState = WishlistState(),
            cartState = CartState(),
            isOffline = false,
            popBackStack = {},
            navigateToCart = {},
            addToWishlist = {},
            removeWishlist = {},
            addToCart = {},
            refresh = {},
            resetErrorState = {},
            onShowSnackbar = { _, _ -> false }
        )
    }
}