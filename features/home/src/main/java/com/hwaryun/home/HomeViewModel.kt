package com.hwaryun.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.hwaryun.datasource.datastore.UserPreferenceManager
import com.hwaryun.datasource.repository.FoodRepository
import com.hwaryun.domain.mapper.toFood
import com.hwaryun.domain.mapper.toUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userPreferenceManager: UserPreferenceManager,
    foodRepository: FoodRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val data = userPreferenceManager.user.first()
            if (data != null) {
                _uiState.update {
                    it.copy(user = data.toUser())
                }
            }
        }
    }

    val foods = foodRepository.getFoods()
        .map { pagingData ->
            pagingData.map {
                it.toFood()
            }
        }
        .cachedIn(viewModelScope)

    val newFoods = foodRepository.getFoods("new_food")
        .map { pagingData ->
            pagingData.map {
                it.toFood()
            }
        }
        .cachedIn(viewModelScope)

    val popularFoods = foodRepository.getFoods("popular")
        .map { pagingData ->
            pagingData.map {
                it.toFood()
            }
        }
        .cachedIn(viewModelScope)

    val recommendedFoods = foodRepository.getFoods("recommended")
        .map { pagingData ->
            pagingData.map {
                it.toFood()
            }
        }
        .cachedIn(viewModelScope)
}