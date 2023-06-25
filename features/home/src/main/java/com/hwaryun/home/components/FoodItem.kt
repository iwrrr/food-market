package com.hwaryun.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
import com.hwaryun.designsystem.components.atoms.AsphaltText
import com.hwaryun.designsystem.ui.FoodMarketTheme
import com.hwaryun.designsystem.ui.asphalt.AsphaltTheme
import com.hwaryun.designsystem.utils.singleClick
import com.hwaryun.domain.model.Food

@Composable
fun FoodItem(
    modifier: Modifier = Modifier,
    food: Food,
    isLoading: Boolean = false,
    onFoodClick: (Int) -> Unit
) {
    val context = LocalContext.current

    Card(
        modifier = modifier.singleClick { onFoodClick(food.id) },
        shape = AsphaltTheme.shapes.small,
        colors = CardDefaults.cardColors(AsphaltTheme.colors.cool_gray_1cCp_100)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(food.picturePath)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.ic_placeholder),
            error = painterResource(R.drawable.ic_placeholder),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(120.dp)
                .placeholder(
                    visible = isLoading,
                    highlight = PlaceholderHighlight.shimmer(),
                    color = PlaceholderDefaults.color(),
                    shape = RoundedCornerShape(8.dp)
                )
                .clip(AsphaltTheme.shapes.small)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            AsphaltText(
                text = food.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .placeholder(
                        visible = isLoading,
                        highlight = PlaceholderHighlight.shimmer(),
                        color = PlaceholderDefaults.color(),
                        shape = RoundedCornerShape(8.dp)
                    ),
                style = AsphaltTheme.typography.titleSmallDemi,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
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
                    text = food.rate,
                    modifier = Modifier
                        .placeholder(
                            visible = isLoading,
                            highlight = PlaceholderHighlight.shimmer(),
                            color = PlaceholderDefaults.color(),
                            shape = RoundedCornerShape(8.dp)
                        ),
                    style = AsphaltTheme.typography.captionSmallDemi
                )
            }
        }
    }
}

@Preview
@Composable
private fun DefaultPreview() {
    FoodMarketTheme {
        FoodItem(
            food = Food(
                description = "",
                id = 0,
                ingredients = "",
                name = "Food",
                picturePath = "",
                price = 0,
                rate = "5.0",
                types = ""
            ),
            onFoodClick = {}
        )
    }
}