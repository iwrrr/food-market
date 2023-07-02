package com.hwaryun.search

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hwaryun.common.ext.orZero
import com.hwaryun.common.ext.toNumberFormat
import com.hwaryun.designsystem.R
import com.hwaryun.designsystem.components.atoms.AsphaltText
import com.hwaryun.designsystem.components.organisms.FoodImage
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
    Column(
        modifier = modifier
            .background(AsphaltTheme.colors.pure_white_500)
            .singleClick { onFoodClick(food?.id.orZero()) }
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            FoodImage(url = food?.picturePath, rate = "${food?.rate}")
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
                    style = AsphaltTheme.typography.titleModerateBold,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(4.dp))
                AsphaltText(
                    text = stringResource(id = R.string.currency, food?.price.toNumberFormat()),
                    modifier = Modifier.fillMaxWidth(),
                    style = AsphaltTheme.typography.captionSmallDemi,
                    overflow = TextOverflow.Ellipsis,
                    color = AsphaltTheme.colors.cool_gray_500,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(8.dp))
                Divider(thickness = 1.dp, color = AsphaltTheme.colors.cool_gray_1cCp_100)
                Spacer(modifier = Modifier.height(8.dp))
                AsphaltText(
                    text = stringResource(
                        id = R.string.delivered_time,
                        food?.deliveryTime.orZero()
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    style = AsphaltTheme.typography.captionSmallDemi,
                    color = AsphaltTheme.colors.sub_black_500,
                )
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DefaultPreview() {
    FoodMarketTheme {
        FoodItem(
            food = Food(
                description = "",
                id = 0,
                ingredients = "",
                name = "Kopi Beanspot",
                picturePath = "",
                price = 0,
                rate = 0f,
                types = "",
                deliveryTime = 10
            ),
            onFoodClick = {},
        )
    }
}