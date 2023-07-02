package com.hwaryun.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hwaryun.common.ConnectivityException
import com.hwaryun.common.ext.subscribe
import com.hwaryun.datasource.datastore.UserPreferenceManager
import com.hwaryun.domain.mapper.toUser
import com.hwaryun.domain.usecase.food.GetTrendingFoodsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTrendingFoodsUseCase: GetTrendingFoodsUseCase,
    private val userPreferenceManager: UserPreferenceManager
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    init {
        getUser()
        getTrendingFoods()
    }

    fun getTrendingFoods() {
        viewModelScope.launch {
            getTrendingFoodsUseCase.invoke().collect { result ->
                result.subscribe(
                    doOnLoading = {
                        _state.update { state ->
                            state.copy(
                                isOffline = false,
                                isLoading = true
                            )
                        }
                    },
                    doOnSuccess = {
                        result.value?.let { foods ->
                            _state.update { state ->
                                state.copy(
                                    foods = foods,
                                    isOffline = false,
                                    isLoading = false
                                )
                            }
                        }
                    },
                    doOnEmpty = {
                        _state.update { state ->
                            state.copy(
                                foods = emptyList(),
                                isOffline = false,
                                isLoading = false
                            )
                        }
                    },
                    doOnError = {
                        when (it.throwable) {
                            is ConnectivityException -> {
                                _state.update { state ->
                                    state.copy(
                                        isOffline = true,
                                        isLoading = false,
                                        error = it.throwable?.message ?: "Unexpected error occurred"
                                    )
                                }
                            }

                            else -> {
                                _state.update { state ->
                                    state.copy(
                                        isOffline = false,
                                        isLoading = false,
                                        error = it.throwable?.message ?: "Unexpected error occurred"
                                    )
                                }
                            }
                        }
                    }
                )
            }
        }
    }

    private fun getUser() {
        viewModelScope.launch {
            val data = userPreferenceManager.user.firstOrNull()
            if (data != null) {
                _state.update {
                    it.copy(
                        user = data.toUser()
                    )
                }
            }
        }
    }
}