package com.hwaryun.food_detail

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarConfig
import com.hwaryun.designsystem.R
import com.hwaryun.designsystem.components.FoodMarketButton
import com.hwaryun.designsystem.components.FoodMarketCounterButton
import com.hwaryun.designsystem.ui.FoodMarketTheme

@Composable
internal fun FoodDetailRoute(
    onOrderClick: (foodId: Int?, qty: Int, total: Int) -> Unit,
    viewModel: FoodDetailViewModel = hiltViewModel()
) {
    val foodDetailUiState by viewModel.foodDetailUiState.collectAsStateWithLifecycle()

    FoodDetailScreen(
        foodDetailUiState = foodDetailUiState,
        onOrderClick = onOrderClick,
        addQuantity = viewModel::addQuantity,
        reduceQuantity = viewModel::reduceQuantity
    )
}

@Composable
fun FoodDetailScreen(
    foodDetailUiState: FoodDetailUiState,
    addQuantity: (Int) -> Unit,
    reduceQuantity: (Int) -> Unit,
    onOrderClick: (foodId: Int?, qty: Int, total: Int) -> Unit,
) {
    val context = LocalContext.current

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(foodDetailUiState.food?.picturePath)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.ic_placeholder),
            error = painterResource(R.drawable.ic_placeholder),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .placeholder(
                    visible = foodDetailUiState.isLoading,
                    highlight = PlaceholderHighlight.shimmer(),
                    color = PlaceholderDefaults.color(),
                    shape = RoundedCornerShape(8.dp)
                )
                .heightIn(min = 330.dp, max = 500.dp)
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 420.dp, max = 420.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 24.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(2f),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = foodDetailUiState.food?.name.toString(),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .placeholder(
                                        visible = foodDetailUiState.isLoading,
                                        highlight = PlaceholderHighlight.shimmer(),
                                        color = PlaceholderDefaults.color(),
                                        shape = RoundedCornerShape(8.dp)
                                    ),
                                style = MaterialTheme.typography.titleLarge,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RatingBar(
                                    value = foodDetailUiState.food?.rate?.toFloat() ?: 0f,
                                    config = RatingBarConfig()
                                        .isIndicator(true)
                                        .size(16.dp),
                                    onValueChange = {},
                                    onRatingChanged = {}
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = foodDetailUiState.food?.rate.toString(),
                                    modifier = Modifier
                                        .placeholder(
                                            visible = foodDetailUiState.isLoading,
                                            highlight = PlaceholderHighlight.shimmer(),
                                            color = PlaceholderDefaults.color(),
                                            shape = RoundedCornerShape(8.dp)
                                        ),
                                    style = MaterialTheme.typography.bodySmall,
                                    overflow = TextOverflow.Ellipsis,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    maxLines = 1
                                )
                            }
                        }

                        FoodMarketCounterButton(
                            modifier = Modifier
                                .wrapContentSize(Alignment.CenterEnd)
                                .weight(1f),
                            value = foodDetailUiState.qty,
                            onDecrementClick = {
                                reduceQuantity(it)
                            },
                            onIncrementClick = {
                                addQuantity(it)
                            },
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = foodDetailUiState.food?.description.toString(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .placeholder(
                                visible = foodDetailUiState.isLoading,
                                highlight = PlaceholderHighlight.shimmer(),
                                color = PlaceholderDefaults.color(),
                                shape = RoundedCornerShape(8.dp)
                            ),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 4
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Ingredients:",
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = foodDetailUiState.food?.ingredients.toString(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .placeholder(
                                visible = foodDetailUiState.isLoading,
                                highlight = PlaceholderHighlight.shimmer(),
                                color = PlaceholderDefaults.color(),
                                shape = RoundedCornerShape(8.dp)
                            ),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 2
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        ) {
                            Text(
                                text = "Total Price:",
                                modifier = Modifier.fillMaxWidth(),
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "IDR ${foodDetailUiState.totalPrice}",
                                modifier = Modifier
                                    .then(
                                        if (foodDetailUiState.isLoading) {
                                            Modifier.fillMaxWidth(1f / 2)
                                        } else {
                                            Modifier
                                        }
                                    )
                                    .placeholder(
                                        visible = foodDetailUiState.isLoading,
                                        highlight = PlaceholderHighlight.shimmer(),
                                        color = PlaceholderDefaults.color(),
                                        shape = RoundedCornerShape(8.dp)
                                    ),
                                style = MaterialTheme.typography.titleLarge,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1
                            )
                        }
                        FoodMarketButton(
                            text = "Order Now",
                            modifier = Modifier
                                .fillMaxWidth()
                                .placeholder(
                                    visible = foodDetailUiState.isLoading,
                                    highlight = PlaceholderHighlight.shimmer(),
                                    color = PlaceholderDefaults.color(),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .weight(1f),
                            onClick = {
                                if (foodDetailUiState.isLoading) {
                                    return@FoodMarketButton
                                } else {
                                    onOrderClick(foodDetailUiState.food?.id, foodDetailUiState.qty, foodDetailUiState.totalPrice)
                                }
                            },
                        )
                    }
                }
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun FoodDetailScreenPreview() {
    FoodMarketTheme {
        FoodDetailScreen(
            foodDetailUiState = FoodDetailUiState(),
            addQuantity = {},
            reduceQuantity = {},
            onOrderClick = { _, _, _ -> }
        )
    }
}