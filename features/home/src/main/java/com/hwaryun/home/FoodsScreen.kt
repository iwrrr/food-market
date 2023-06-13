package com.hwaryun.home

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.hwaryun.datasource.paging.subscribe
import com.hwaryun.designsystem.ui.FoodMarketTheme
import com.hwaryun.domain.model.Food
import com.hwaryun.home.components.PlaceholderVerticalItem
import com.hwaryun.home.components.VerticalFoodItem

@Composable
fun FoodsScreen(
    foods: LazyPagingItems<Food>,
    onFoodClick: (Int) -> Unit,
) {
    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 800.dp, max = 800.dp)
            .background(MaterialTheme.colorScheme.surface),
        contentPadding = PaddingValues(top = 8.dp, bottom = 160.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        foods.loadState.refresh.subscribe(
            doOnLoading = {
                item {
                    repeat(10) {
                        PlaceholderVerticalItem()
                    }
                }
            },
            doOnNotLoading = {
                items(foods, key = { it.id }) { food ->
                    if (food != null) {
                        VerticalFoodItem(food = food, onFoodClick = onFoodClick)
                    } else {
                        PlaceholderVerticalItem()
                    }
                }
            }
        )

        foods.loadState.append.subscribe(
            doOnLoading = {
                item {
                    PlaceholderVerticalItem()
                }
            }
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun FoodScreenPreview() {
    FoodMarketTheme {
        //        FoodsScreen(
        //            onFoodClick = {}
        //        )
    }
}