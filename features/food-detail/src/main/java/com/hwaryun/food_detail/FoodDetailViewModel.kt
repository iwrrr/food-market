package com.hwaryun.food_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hwaryun.common.ext.suspendSubscribe
import com.hwaryun.domain.model.Food
import com.hwaryun.domain.usecase.cart.AddToCartUseCase
import com.hwaryun.domain.usecase.food.GetFoodDetailUseCase
import com.hwaryun.food_detail.navigation.FOOD_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodDetailViewModel @Inject constructor(
    private val getFoodDetailUseCase: GetFoodDetailUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _foodDetailState = MutableStateFlow(FoodDetailState())
    val foodDetailState = _foodDetailState.asStateFlow()

    private val _addToCartState = MutableStateFlow(AddToCartState())
    val addToCartState = _addToCartState.asStateFlow()

    init {
        val foodId = savedStateHandle.get<Int>(FOOD_ID) ?: 0
        getFoodDetail(foodId)
    }

    private fun getFoodDetail(foodId: Int) {
        viewModelScope.launch {
            getFoodDetailUseCase.invoke(foodId).collect { result ->
                result.suspendSubscribe(
                    doOnLoading = {
                        _foodDetailState.update { state ->
                            state.copy(
                                isLoading = true
                            )
                        }
                    },
                    doOnSuccess = {
                        _foodDetailState.update { state ->
                            state.copy(
                                food = it.value,
                                isLoading = false,
                            )
                        }
                    },
                    doOnError = {
                        _foodDetailState.update { state ->
                            state.copy(
                                isLoading = false,
                                error = it.throwable?.message ?: "Unexpected error accrued"
                            )
                        }
                    }
                )
            }
        }
    }

    fun addToCart(food: Food) {
        viewModelScope.launch {
            addToCartUseCase.invoke(food).collect { result ->
                result.suspendSubscribe(
                    doOnLoading = {
                        _addToCartState.update { state ->
                            state.copy(
                                isLoading = true
                            )
                        }
                    },
                    doOnSuccess = {
                        _addToCartState.update { state ->
                            state.copy(
                                addToCart = it.value,
                                isLoading = false,
                            )
                        }
                    },
                    doOnError = {
                        _addToCartState.update { state ->
                            state.copy(
                                isLoading = false,
                                error = it.throwable?.message ?: "Unexpected error accrued"
                            )
                        }
                    }
                )
            }
        }
    }

    fun resetErrorState() {
        _foodDetailState.update {
            it.copy(error = "")
        }
    }
}