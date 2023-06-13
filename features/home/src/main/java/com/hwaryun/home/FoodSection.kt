package com.hwaryun.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.hwaryun.datasource.paging.subscribe
import com.hwaryun.domain.model.Food
import com.hwaryun.home.components.HorizontalFoodItem
import com.hwaryun.home.components.PlaceholderHorizontalItem

@Composable
fun FoodSection(
    listState: LazyListState,
    foods: LazyPagingItems<Food>,
    onFoodClick: (Int) -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(24.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        state = listState,
    ) {
        foods.loadState.refresh.subscribe(
            doOnLoading = {
                item {
                    repeat(5) {
                        PlaceholderHorizontalItem()
                        Spacer(modifier = Modifier.width(24.dp))
                    }
                }
            },
            doOnNotLoading = {
                items(foods, key = { it.id }) { food ->
                    if (food != null) {
                        HorizontalFoodItem(food = food, onFoodClick = onFoodClick)
                    } else {
                        PlaceholderHorizontalItem()
                    }
                }
            }
        )
        foods.loadState.append.subscribe(
            doOnLoading = {
                item {
                    PlaceholderHorizontalItem()
                }
            }
        )
    }
}