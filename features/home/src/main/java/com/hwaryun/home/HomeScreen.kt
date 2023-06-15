package com.hwaryun.home

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.hwaryun.designsystem.components.FoodMarketTabSection
import com.hwaryun.designsystem.components.TabItem
import com.hwaryun.designsystem.layout.ChildLayout
import com.hwaryun.designsystem.layout.VerticalScrollLayout
import com.hwaryun.designsystem.ui.FoodMarketTheme
import com.hwaryun.designsystem.ui.Yellow
import com.hwaryun.domain.model.Food
import com.hwaryun.home.components.HeaderHome

const val FOOD_SECTION = "food_section"
const val FOOD_TAB_SECTION = "food_tab_section"

@Composable
internal fun HomeRoute(
    onFoodClick: (Int) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val homeUiState by viewModel.homeUiState.collectAsStateWithLifecycle()
    val foods = viewModel.foods.collectAsLazyPagingItems()
    val newFoods = viewModel.newFoods.collectAsLazyPagingItems()
    val popularFoods = viewModel.popularFoods.collectAsLazyPagingItems()
    val recommendedFoods = viewModel.recommendedFoods.collectAsLazyPagingItems()

    HomeScreen(
        homeUiState = homeUiState,
        foods = foods,
        newFoods = newFoods,
        popularFoods = popularFoods,
        recommendedFoods = recommendedFoods,
        onFoodClick = onFoodClick
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    homeUiState: HomeUiState,
    foods: LazyPagingItems<Food>,
    newFoods: LazyPagingItems<Food>,
    popularFoods: LazyPagingItems<Food>,
    recommendedFoods: LazyPagingItems<Food>,
    onFoodClick: (Int) -> Unit,
) {
    Scaffold(
        topBar = {
            HeaderHome(uiState = homeUiState)
        },
        content = { innerPadding ->

            val pullRefreshState = rememberPullRefreshState(false, {
                foods.refresh()
                newFoods.refresh()
                popularFoods.refresh()
                recommendedFoods.refresh()
            })

            Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
                VerticalScrollLayout(
                    modifier = Modifier
                        .padding(
                            PaddingValues(
                                top = innerPadding.calculateTopPadding(),
                                bottom = 58.dp
                            )
                        ),
                    state = rememberLazyListState(),
                    ChildLayout(
                        contentType = FOOD_SECTION,
                        content = {
                            FoodSection(
                                listState = rememberLazyListState(),
                                foods = foods,
                                onFoodClick = onFoodClick,
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
                                        screen = {
                                            FoodsScreen(
                                                foods = newFoods,
                                                onFoodClick = onFoodClick
                                            )
                                        }
                                    ),
                                    TabItem(
                                        title = "Popular",
                                        screen = {
                                            FoodsScreen(
                                                foods = popularFoods,
                                                onFoodClick = onFoodClick
                                            )
                                        }
                                    ),
                                    TabItem(
                                        title = "Recommended",
                                        screen = {
                                            FoodsScreen(
                                                foods = recommendedFoods,
                                                onFoodClick = onFoodClick
                                            )
                                        }
                                    ),
                                )
                            )
                        }
                    ),
                )

                PullRefreshIndicator(
                    refreshing = false,
                    state = pullRefreshState,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(
                            PaddingValues(
                                top = innerPadding.calculateTopPadding()
                            )
                        ),
                    contentColor = Yellow
                )
            }
        }
    )
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DefaultPreview() {
    FoodMarketTheme {
        //        HomeScreen(onFoodClick = {})
    }
}