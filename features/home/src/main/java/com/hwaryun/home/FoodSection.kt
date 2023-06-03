package com.hwaryun.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.hwaryun.home.component.HorizontalFoodItem

@Composable
fun FoodSection(
    listState: LazyListState,
    onFoodClick: () -> Unit
) {
    val itemsList = (0..5).toList()

    LazyRow(
        contentPadding = PaddingValues(24.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        state = listState,
    ) {
        items(
            items = itemsList,
            key = { item -> item }
        ) {
            HorizontalFoodItem(onFoodClick = onFoodClick)
        }
    }
}