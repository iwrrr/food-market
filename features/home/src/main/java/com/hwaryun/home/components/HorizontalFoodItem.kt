package com.hwaryun.home.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarConfig
import com.hwaryun.designsystem.R
import com.hwaryun.designsystem.components.FoodMarketCard
import com.hwaryun.designsystem.ui.FoodMarketTheme
import com.hwaryun.domain.model.Food

@Composable
fun HorizontalFoodItem(
    food: Food,
    modifier: Modifier = Modifier,
    onFoodClick: (Int) -> Unit
) {
    val context = LocalContext.current

    FoodMarketCard(
        modifier = modifier
            .width(200.dp)
            .height(210.dp),
        shape = RoundedCornerShape(8.dp),
        onClick = { onFoodClick(food.id) }
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
                .fillMaxWidth()
                .height(140.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = food.name,
                modifier = Modifier.fillMaxWidth(),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(4.dp))
            RatingBar(
                value = food.rate.toFloat(),
                config = RatingBarConfig()
                    .isIndicator(true)
                    .size(16.dp),
                onValueChange = {},
                onRatingChanged = {}
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HorizontalFoodItemPreview() {
    FoodMarketTheme {
        HorizontalFoodItem(
            food = Food(
                description = "",
                id = 0,
                ingredients = "",
                name = "",
                picturePath = "",
                price = 0,
                rate = "",
                types = "",
            ),
            onFoodClick = {},
        )
    }
}