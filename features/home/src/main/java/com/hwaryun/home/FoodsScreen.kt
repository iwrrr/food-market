package com.hwaryun.home

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hwaryun.designsystem.ui.FoodMarketTheme
import com.hwaryun.home.components.VerticalFoodItem

@Composable
fun FoodsScreen(
    onFoodClick: () -> Unit,
) {
    val listState = rememberLazyListState()
    val itemsList = (0..35).toList()

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 800.dp)
            .background(MaterialTheme.colorScheme.surface),
        contentPadding = PaddingValues(top = 8.dp, bottom = 160.dp),
        state = listState,
    ) {
        items(items = itemsList, key = { item -> item.hashCode() }) {
            VerticalFoodItem(
                onFoodClick = onFoodClick
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun FoodScreenPreview() {
    FoodMarketTheme {
        FoodsScreen(
            onFoodClick = {}
        )
    }
}