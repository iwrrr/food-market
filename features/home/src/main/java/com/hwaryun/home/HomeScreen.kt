package com.hwaryun.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hwaryun.designsystem.component.FoodMarketTabSection
import com.hwaryun.designsystem.component.TabItem
import com.hwaryun.designsystem.layout.ChildLayout
import com.hwaryun.designsystem.layout.VerticalScrollLayout
import com.hwaryun.designsystem.ui.FoodMarketTheme
import com.hwaryun.home.components.HeaderHome

const val FOOD_SECTION = "food_section"
const val FOOD_TAB_SECTION = "food_tab_section"

@Composable
internal fun HomeRoute(onFoodClick: () -> Unit) {
    HomeScreen(onFoodClick = onFoodClick)
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun HomeScreen(onFoodClick: () -> Unit) {
    Scaffold(
        topBar = {
            HeaderHome()
        },
        content = { innerPadding ->
            VerticalScrollLayout(
                modifier = Modifier.padding(innerPadding),
                state = rememberLazyListState(),
                ChildLayout(
                    contentType = FOOD_SECTION,
                    content = {
                        FoodSection(
                            listState = rememberLazyListState(),
                            onFoodClick = onFoodClick
                        )
                    }
                ),
                ChildLayout(
                    contentType = FOOD_TAB_SECTION,
                    isSticky = true,
                    content = {
                        FoodMarketTabSection(
                            tabItems = listOf(
                                TabItem(
                                    title = "New Taste",
                                    screen = { FoodsScreen(onFoodClick = onFoodClick) }
                                ),
                                TabItem(
                                    title = "Popular",
                                    screen = { FoodsScreen(onFoodClick = onFoodClick) }
                                ),
                                TabItem(
                                    title = "Recommended",
                                    screen = { FoodsScreen(onFoodClick = onFoodClick) }
                                ),
                            )
                        )
                    }
                ),
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeScreen(
    items: List<String>, onSave: (name: String) -> Unit, modifier: Modifier = Modifier
) {
    Column(modifier) {
        var nameMyModel by remember { mutableStateOf("Compose") }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TextField(value = nameMyModel, onValueChange = { nameMyModel = it })

            Button(modifier = Modifier.width(96.dp), onClick = { onSave(nameMyModel) }) {
                Text("Save")
            }
        }
        items.forEach {
            Text("Saved item: $it")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    FoodMarketTheme {
        HomeScreen(listOf("Compose", "Room", "Kotlin"), onSave = {})
    }
}

@Preview(showBackground = true, widthDp = 480)
@Composable
private fun PortraitPreview() {
    FoodMarketTheme {
        HomeScreen(listOf("Compose", "Room", "Kotlin"), onSave = {})
    }
}