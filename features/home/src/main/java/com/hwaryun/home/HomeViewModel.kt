package com.hwaryun.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hwaryun.common.ext.subscribe
import com.hwaryun.domain.usecase.food.GetTrendingFoodsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTrendingFoodsUseCase: GetTrendingFoodsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    init {
        getTrendingFoods()
    }

    private fun getTrendingFoods() {
        viewModelScope.launch {
            getTrendingFoodsUseCase.invoke().collect { result ->
                result.subscribe(
                    doOnLoading = {
                        _state.update {
                            it.copy(
                                isLoading = true
                            )
                        }
                    },
                    doOnSuccess = {
                        result.value?.let { foods ->
                            _state.update {
                                it.copy(
                                    foods = foods,
                                    isLoading = false
                                )
                            }
                        }
                    },
                    doOnEmpty = {
                        _state.update {
                            it.copy(
                                foods = emptyList(),
                                isLoading = false
                            )
                        }
                    },
                    doOnError = {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = result.throwable?.message.toString()
                            )
                        }
                    }
                )
            }
        }
    }
}