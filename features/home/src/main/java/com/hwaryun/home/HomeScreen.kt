package com.hwaryun.home

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hwaryun.designsystem.R
import com.hwaryun.designsystem.components.atoms.AsphaltText
import com.hwaryun.designsystem.components.molecules.AsphaltSearchBar
import com.hwaryun.designsystem.ui.FoodMarketTheme
import com.hwaryun.designsystem.ui.asphalt.AsphaltTheme
import com.hwaryun.designsystem.utils.singleClick
import com.hwaryun.domain.model.Food
import com.hwaryun.home.components.FoodItem
import com.hwaryun.home.components.HeaderHome

const val FOOD_SECTION = "food_section"
const val FOOD_TAB_SECTION = "food_tab_section"

@Composable
internal fun HomeRoute(
    onFoodClick: (Int) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    HomeScreen(
        state = state,
        onFoodClick = onFoodClick,
        onTrendingSectionClick = {}
    )
}

@Composable
fun HomeScreen(
    state: HomeState,
    onFoodClick: (Int) -> Unit,
    onTrendingSectionClick: () -> Unit
) {
    Scaffold(
        topBar = {
            HeaderHome()
        },
        containerColor = AsphaltTheme.colors.pure_white_500,
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(top = innerPadding.calculateTopPadding())
            ) {
                Row(modifier = Modifier.padding(horizontal = 24.dp)) {
                    AsphaltSearchBar(
                        query = "",
                        onQueryChange = {},
                        onSearchFocusChange = {},
                        onClearQuery = {},
                        onBack = {},
                        placeholder = "Perfect food for lunch",
                        enabled = false
                    )
                }
                Box {
                    LazyColumn {
                        item {
                            TrendingSection()
                        }
                        item {
                            TrendingContent(foods = state.foods, onFoodClick = onFoodClick)
                        }
                        item {
                            PromoSection()
                        }
                        item {
                            PromoContent()
                        }
                    }

                }
            }
        }
    )
}

@Composable
private fun TrendingSection() {
    Spacer(modifier = Modifier.height(16.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            AsphaltText(
                text = "Trending Now",
                modifier = Modifier.fillMaxWidth(),
                style = AsphaltTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            AsphaltText(
                text = "The food menu based on our data",
                modifier = Modifier.fillMaxWidth(),
                color = AsphaltTheme.colors.cool_gray_500,
                style = AsphaltTheme.typography.bodySmall
            )
        }
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_right),
            contentDescription = "More foods",
            modifier = Modifier.singleClick { }
        )
    }
}

@Composable
private fun TrendingContent(
    foods: List<Food>,
    onFoodClick: (Int) -> Unit
) {
    Spacer(modifier = Modifier.height(16.dp))
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.height(200.dp),
        contentPadding = PaddingValues(horizontal = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(foods) { food ->
            FoodItem(
                food = food,
                isLoading = false,
                onFoodClick = onFoodClick
            )
        }
    }
}

@Composable
private fun PromoSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            AsphaltText(
                text = "Promotion",
                modifier = Modifier.fillMaxWidth(),
                style = AsphaltTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            AsphaltText(
                text = "All best deal only for you",
                modifier = Modifier.fillMaxWidth(),
                color = AsphaltTheme.colors.cool_gray_500,
                style = AsphaltTheme.typography.bodySmall
            )
        }
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_right),
            contentDescription = "More foods",
            modifier = Modifier.singleClick { }
        )
    }
}

@Composable
private fun PromoContent() {
    val promotions = (0..1).toList()
    Spacer(modifier = Modifier.height(16.dp))
    LazyRow(
        contentPadding = PaddingValues(
            start = 24.dp,
            end = 24.dp,
            bottom = 100.dp
        ),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(promotions) {
            Image(
                painter = painterResource(id = R.drawable.promotion_1),
                contentDescription = null,
                modifier = Modifier
                    .fillParentMaxWidth(0.9f)
                    .aspectRatio(16f / 9f)
                    .height(140.dp)
                    .clip(AsphaltTheme.shapes.small),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DefaultPreview() {
    FoodMarketTheme {
        HomeScreen(
            state = HomeState(),
            onFoodClick = {},
            onTrendingSectionClick = {}
        )
    }
}