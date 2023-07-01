package com.hwaryun.search

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.hwaryun.datasource.paging.subscribe
import com.hwaryun.designsystem.R
import com.hwaryun.designsystem.components.atoms.AsphaltText
import com.hwaryun.designsystem.components.molecules.AsphaltSearchBar
import com.hwaryun.designsystem.ui.FoodMarketTheme
import com.hwaryun.designsystem.ui.Yellow
import com.hwaryun.designsystem.ui.asphalt.AsphaltTheme
import com.hwaryun.domain.model.Food
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
internal fun SearchRoute(
    onFoodClick: (Int) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val query by viewModel.query.collectAsStateWithLifecycle()
    val foods = viewModel.foods.collectAsLazyPagingItems()

    SearchScreen(
        state = state,
        query = query,
        foods = foods,
        onFoodClick = onFoodClick,
        onShowSnackbar = onShowSnackbar,
        onQueryChange = viewModel::onQueryChange,
        onFocusChange = viewModel::onFocusChange,
        resetErrorState = viewModel::resetErrorState,
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchScreen(
    state: SearchState,
    query: String,
    foods: LazyPagingItems<Food>,
    onFoodClick: (Int) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    onQueryChange: (String) -> Unit,
    onFocusChange: (Boolean) -> Unit,
    resetErrorState: () -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    LaunchedEffect(state) {
        if (state.error.isNotEmpty()) {
            onShowSnackbar(state.error, null)
            resetErrorState()
        }
    }

    Scaffold(
        modifier = Modifier.statusBarsPadding(),
        containerColor = AsphaltTheme.colors.pure_white_500,
        topBar = {
            Column(
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp),
            ) {
                AsphaltSearchBar(
                    query = query,
                    modifier = Modifier
                        .focusRequester(focusRequester)
                        .onFocusChanged {
                            if (it.hasFocus) {
                                keyboardController?.show()
                            }
                        },
                    onQueryChange = onQueryChange,
                    onSearchFocusChange = onFocusChange,
                    onClearQuery = { onQueryChange("") },
                    onBack = {},
                    placeholder = "Hari ini makan apa ya?",
                    focused = state.focused
                )
            }
        }
    ) { innerPadding ->
        FoodContent(
            innerPadding = innerPadding,
            foods = foods,
            onFoodClick = onFoodClick
        )
    }
}

@Composable
private fun FoodContent(
    innerPadding: PaddingValues,
    foods: LazyPagingItems<Food>,
    onFoodClick: (Int) -> Unit,
) {
    val shouldShowEmptyScreen = foods.itemCount <= 0

    LazyColumn(
        modifier = Modifier
            .background(AsphaltTheme.colors.pure_white_500)
            .padding(innerPadding),
        contentPadding = PaddingValues(bottom = 64.dp)
    ) {
        foods.loadState.refresh.subscribe(
            doOnLoading = {
                item {
                    repeat(10) {
                        PlaceholderFoodItem()
                    }
                }
            },
            doOnNotLoading = {
                when {
                    shouldShowEmptyScreen -> {
                        item {
                            EmptyContent()
                        }
                    }

                    else -> {
                        items(foods, key = { it.id }) { food ->
                            if (food != null) {
                                FoodItem(food = food, onFoodClick = onFoodClick)
                            } else {
                                PlaceholderFoodItem()
                            }
                        }
                    }
                }
            }
        )
    }
}

@Composable
private fun EmptyContent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(150.dp))
        Image(
            painter = painterResource(id = R.drawable.ic_order_empty),
            contentDescription = null,
            modifier = Modifier
                .width(200.dp)
                .height(220.dp)
        )
        Spacer(modifier = Modifier.height(28.dp))
        AsphaltText(
            text = "Duh laper!",
            style = AsphaltTheme.typography.titleExtraLarge
        )
        Spacer(modifier = Modifier.height(8.dp))
        AsphaltText(
            text = "Sabar ya penjual lagi masak makanannya!",
            style = AsphaltTheme.typography.titleSmallDemi,
            textAlign = TextAlign.Center,
            color = AsphaltTheme.colors.cool_gray_500
        )
    }
}

@Composable
private fun LoadingContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = Yellow)
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DefaultPreview() {
    val data = emptyList<Food>()
    val flow = MutableStateFlow(PagingData.from(data))
    FoodMarketTheme {
        SearchScreen(
            state = SearchState(
                query = "",
                focused = false,
                isLoading = false
            ),
            query = "",
            foods = flow.collectAsLazyPagingItems(),
            onFoodClick = {},
            onShowSnackbar = { _, _ -> false },
            onQueryChange = { _ -> },
            onFocusChange = { _ -> },
            resetErrorState = {},
        )
    }
}