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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Icon
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

    FoodDetailScreen(
        foodDetailState = foodDetailState,
        wishlistState = wishlistState,
        cartState = cartState,
        popBackStack = popBackStack,
        navigateToCart = navigateToCart,
        addToWishlist = viewModel::addToWishlist,
        removeWishlist = viewModel::removeWishlist,
        addToCart = viewModel::addToCart,
        shouldDisplayWishlistSnackbar = viewModel.shouldDisplayWishlistSnackbar,
        refresh = viewModel::refresh,
        clearSnackbarState = viewModel::clearSnackbarState,
        resetErrorState = viewModel::resetErrorState,
        onShowSnackbar = onShowSnackbar
    )
}

@Composable
fun FoodDetailScreen(
    foodDetailState: FoodDetailState,
    wishlistState: WishlistState,
    cartState: CartState,
    popBackStack: () -> Unit,
    navigateToCart: () -> Unit,
    addToWishlist: (Food?) -> Unit,
    removeWishlist: (Int?) -> Unit,
    addToCart: (Food) -> Unit,
    shouldDisplayWishlistSnackbar: Boolean,
    refresh: () -> Unit,
    clearSnackbarState: () -> Unit,
    resetErrorState: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
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

    val wishlistStatusMessage = if (wishlistState.isWishlist) {
        stringResource(id = R.string.success_add_to_wishlist_message)
    } else {
        stringResource(id = R.string.success_remove_wishlist_message)
    }

    LaunchedEffect(shouldDisplayWishlistSnackbar) {
        if (shouldDisplayWishlistSnackbar) {
            onShowSnackbar(wishlistStatusMessage, null)
            clearSnackbarState()
        }
    }

    if (foodDetailState.isOffline) {
        NoConnectionScreen(onTryAgain = refresh)
    } else {
        DetailContent(
            foodDetailState = foodDetailState,
            wishlistState = wishlistState,
            cartState = cartState,
            popBackStack = popBackStack,
            addToWishlist = addToWishlist,
            removeWishlist = removeWishlist,
            addToCart = addToCart,
            refresh = refresh
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun DetailContent(
    foodDetailState: FoodDetailState,
    wishlistState: WishlistState,
    cartState: CartState,
    popBackStack: () -> Unit,
    addToWishlist: (Food?) -> Unit,
    removeWishlist: (Int?) -> Unit,
    addToCart: (Food) -> Unit,
    refresh: () -> Unit,
) {
    val scrollState = rememberScrollState()
    val pullRefreshState = rememberPullRefreshState(refreshing = false, onRefresh = refresh)

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
        content = { innerPadding ->
            Box(
                modifier = Modifier.pullRefresh(pullRefreshState),
                contentAlignment = Alignment.TopCenter,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .padding(
                            top = innerPadding.calculateTopPadding() + 16.dp,
                            start = 16.dp,
                            end = 16.dp,
                            bottom = innerPadding.calculateBottomPadding() + 12.dp,
                        )
                ) {
                    Box {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
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
                        if (foodDetailState.food?.types?.contains("popular") == true && !foodDetailState.isLoading) {
                            AsphaltText(
                                text = "Trending",
                                modifier = Modifier
                                    .offset(x = 12.dp, y = 12.dp)
                                    .clip(AsphaltTheme.shapes.small)
                                    .background(AsphaltTheme.colors.pure_white_500)
                                    .padding(horizontal = 12.dp, vertical = 6.dp),
                                style = AsphaltTheme.typography.captionSmallDemi
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
                                    shape = AsphaltTheme.shapes.small
                                ),
                            style = AsphaltTheme.typography.titleLarge,
                        )

                        FavoriteButton(
                            isChecked = wishlistState.isWishlist,
                            onClick = {
                                if (wishlistState.isWishlist) {
                                    removeWishlist(foodDetailState.food?.id)

                                } else {
                                    addToWishlist(foodDetailState.food)
                                }
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_star),
                            contentDescription = null,
                            modifier = Modifier.size(12.dp),
                            tint = AsphaltTheme.colors.odd_job_orange_500
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        AsphaltText(
                            text = "${foodDetailState.food?.rate.orZero()}",
                            modifier = Modifier
                                .placeholder(
                                    visible = foodDetailState.isLoading,
                                    highlight = PlaceholderHighlight.shimmer(),
                                    color = PlaceholderDefaults.color(),
                                    shape = AsphaltTheme.shapes.small
                                ),
                            style = AsphaltTheme.typography.captionModerateDemi.copy(fontWeight = FontWeight.Bold),
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
                                shape = AsphaltTheme.shapes.small
                            ),
                        style = AsphaltTheme.typography.bodySmall
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsphaltText(
                            text = stringResource(
                                id = R.string.currency,
                                foodDetailState.food?.price.toNumberFormat()
                            ),
                            modifier = Modifier.weight(1f),
                            style = AsphaltTheme.typography.titleExtraLarge.copy(
                                fontWeight = FontWeight.Medium
                            ),
                            textAlign = TextAlign.Center
                        )
                        AsphaltButton(
                            modifier = Modifier.weight(1f),
                            enabled = !foodDetailState.isLoading && !cartState.isLoading,
                            isLoading = foodDetailState.isLoading && cartState.isLoading,
                            onClick = {
                                foodDetailState.food?.let(addToCart)
                            }
                        ) {
                            AsphaltText(text = stringResource(id = R.string.btn_add_to_cart))
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

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun FoodDetailScreenPreview() {
    FoodMarketTheme {
        FoodDetailScreen(
            foodDetailState = FoodDetailState(),
            wishlistState = WishlistState(),
            cartState = CartState(),
            popBackStack = {},
            navigateToCart = {},
            addToWishlist = {},
            removeWishlist = {},
            addToCart = {},
            shouldDisplayWishlistSnackbar = false,
            refresh = {},
            clearSnackbarState = {},
            resetErrorState = {},
            onShowSnackbar = { _, _ -> false }
        )
    }
}