package com.hwaryun.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.hwaryun.datasource.repository.food.FoodRepository
import com.hwaryun.domain.mapper.toFood
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    foodRepository: FoodRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SearchState())
    val state = _state.asStateFlow()

    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    val foods = _query.debounce(300L).flatMapLatest { query ->
        foodRepository.getFoods(name = query)
            .map { pagingData ->
                pagingData.map { it.toFood() }
            }
            .cachedIn(viewModelScope)
    }.catch { e ->
        _state.update {
            it.copy(error = e.message.toString())
        }
    }

    fun onQueryChange(query: String) {
        _query.value = query
    }

    fun onFocusChange(focused: Boolean) {
        _state.update {
            it.copy(focused = focused)
        }
    }

    fun resetErrorState() {
        _state.update {
            it.copy(error = "")
        }
    }
}