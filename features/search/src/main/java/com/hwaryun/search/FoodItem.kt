package com.hwaryun.search

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
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
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarConfig
import com.hwaryun.common.ext.orZero
import com.hwaryun.common.ext.toNumberFormat
import com.hwaryun.designsystem.R
import com.hwaryun.designsystem.components.atoms.AsphaltText
import com.hwaryun.designsystem.ui.FoodMarketTheme
import com.hwaryun.designsystem.ui.asphalt.AsphaltTheme
import com.hwaryun.designsystem.utils.singleClick
import com.hwaryun.domain.model.Food

@Composable
fun FoodItem(
    food: Food?,
    modifier: Modifier = Modifier,
    onFoodClick: (Int) -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .background(AsphaltTheme.colors.pure_white_500)
            .singleClick { onFoodClick(food?.id.orZero()) }
            .padding(horizontal = 24.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(food?.picturePath)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.ic_placeholder),
                error = painterResource(R.drawable.ic_placeholder),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                AsphaltText(
                    text = "${food?.name}",
                    modifier = Modifier.fillMaxWidth(),
                    style = AsphaltTheme.typography.bodyModerate,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                AsphaltText(
                    text = "Rp ${food?.price.toNumberFormat()}",
                    modifier = Modifier.fillMaxWidth(),
                    style = AsphaltTheme.typography.captionSmallBook,
                    overflow = TextOverflow.Ellipsis,
                    color = AsphaltTheme.colors.cool_gray_500,
                    maxLines = 1
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                RatingBar(
                    value = food?.rate.orZero(),
                    config = RatingBarConfig()
                        .isIndicator(true)
                        .size(16.dp),
                    onValueChange = {},
                    onRatingChanged = {}
                )
                Spacer(modifier = Modifier.width(4.dp))
                AsphaltText(
                    text = "${food?.rate}",
                    style = MaterialTheme.typography.bodySmall,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1
                )
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HorizontalFoodItemPreview() {
    FoodMarketTheme {
        FoodItem(
            food = Food(
                description = "",
                id = 0,
                ingredients = "",
                name = "",
                picturePath = "",
                price = 0,
                rate = 0f,
                types = "",
            ),
            onFoodClick = {},
        )
    }
}