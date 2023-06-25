package com.hwaryun.food_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hwaryun.common.ext.suspendSubscribe
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
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _foodDetailState = MutableStateFlow(FoodDetailState())
    val state = _foodDetailState.asStateFlow()

    init {
        val foodId = savedStateHandle.get<Int>(FOOD_ID) ?: 0
        getFoodDetail(foodId)
    }

    private fun getFoodDetail(foodId: Int) {
        viewModelScope.launch {
            getFoodDetailUseCase.invoke(foodId).collect { result ->
                result.suspendSubscribe(
                    doOnLoading = {
                        _foodDetailState.emit(
                            FoodDetailState(
                                isLoading = true
                            )
                        )
                    },
                    doOnSuccess = {
                        _foodDetailState.emit(
                            FoodDetailState(
                                food = it.value,
                                isLoading = false,
                                totalPrice = it.value?.price ?: 0
                            )
                        )
                    },
                    doOnError = {
                        _foodDetailState.emit(
                            FoodDetailState(
                                food = it.value,
                                isLoading = false,
                                error = it.throwable?.message ?: "Unexpected error accrued"
                            )
                        )
                    }
                )
            }
        }
    }

    fun addQuantity(qty: Int) {
        var totalQty = qty
        if (totalQty >= 15) {
            _foodDetailState.update {
                it.copy(qty = 15)
            }
        } else {
            totalQty++
            _foodDetailState.update {
                it.copy(qty = totalQty)
            }
        }

        updateTotalPrice(totalQty)
    }

    fun reduceQuantity(qty: Int) {
        var totalQty = qty
        if (totalQty <= 1) {
            _foodDetailState.update {
                it.copy(qty = 1)
            }
        } else {
            totalQty--
            _foodDetailState.update {
                it.copy(qty = totalQty)
            }
        }

        updateTotalPrice(totalQty)
    }

    private fun updateTotalPrice(qty: Int) {
        _foodDetailState.update {
            it.copy(
                totalPrice = qty * (_foodDetailState.value.food?.price ?: 0)
            )
        }
    }
}