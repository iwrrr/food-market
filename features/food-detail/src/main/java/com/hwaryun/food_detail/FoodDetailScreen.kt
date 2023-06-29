package com.hwaryun.food_detail

import android.annotation.SuppressLint
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
import com.hwaryun.common.ext.toNumberFormat
import com.hwaryun.designsystem.R
import com.hwaryun.designsystem.components.AsphaltAppBar
import com.hwaryun.designsystem.components.atoms.AsphaltButton
import com.hwaryun.designsystem.components.atoms.AsphaltText
import com.hwaryun.designsystem.ui.FoodMarketTheme
import com.hwaryun.designsystem.ui.asphalt.AsphaltTheme
import com.hwaryun.domain.model.Food

@Composable
internal fun FoodDetailRoute(
    navigateToCart: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    viewModel: FoodDetailViewModel = hiltViewModel()
) {
    val foodDetailState by viewModel.foodDetailState.collectAsStateWithLifecycle()
    val addToCartState by viewModel.addToCartState.collectAsStateWithLifecycle()

    FoodDetailScreen(
        foodDetailState = foodDetailState,
        addToCartState = addToCartState,
        navigateToCart = navigateToCart,
        addToCart = viewModel::addToCart,
        resetErrorState = viewModel::resetErrorState,
        onShowSnackbar = onShowSnackbar
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FoodDetailScreen(
    foodDetailState: FoodDetailState,
    addToCartState: AddToCartState,
    navigateToCart: () -> Unit,
    addToCart: (Food) -> Unit,
    resetErrorState: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    LaunchedEffect(addToCartState) {
        if (addToCartState.addToCart != null) {
            navigateToCart()
        }

        if (addToCartState.error.isNotEmpty()) {
            onShowSnackbar(addToCartState.error, null)
            resetErrorState()
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        containerColor = AsphaltTheme.colors.pure_white_500,
        topBar = {
            AsphaltAppBar(title = "Detail", showNavigateBack = true)
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
                AsphaltText(
                    text = "${foodDetailState.food?.name}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .placeholder(
                            visible = foodDetailState.isLoading,
                            highlight = PlaceholderHighlight.shimmer(),
                            color = PlaceholderDefaults.color(),
                            shape = RoundedCornerShape(8.dp)
                        ),
                    style = AsphaltTheme.typography.titleLarge,
                )
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
                        text = "${foodDetailState.food?.rate}",
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
                    text = "${foodDetailState.food?.ingredients}",
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
                        enabled = !addToCartState.isLoading,
                        isLoading = addToCartState.isLoading,
                        onClick = {
                            foodDetailState.food?.let(addToCart)
                        }
                    ) {
                        AsphaltText(text = "Tambah ke keranjang")
                    }
                }
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
            navigateToCart = {},
            addToCart = {},
            resetErrorState = {},
            onShowSnackbar = { _, _ -> false },
            addToCartState = AddToCartState()
        )
    }
}