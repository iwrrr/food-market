package com.hwaryun.food_detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hwaryun.common.ConnectivityException
import com.hwaryun.common.ext.orFalse
import com.hwaryun.common.ext.suspendSubscribe
import com.hwaryun.domain.model.Food
import com.hwaryun.domain.usecase.cart.AddToCartUseCase
import com.hwaryun.domain.usecase.food.GetFoodDetailUseCase
import com.hwaryun.domain.usecase.wishlist.AddToWishlistUseCase
import com.hwaryun.domain.usecase.wishlist.CheckWishlistUseCase
import com.hwaryun.domain.usecase.wishlist.RemoveWishlistUseCase
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
    private val checkWishlistUseCase: CheckWishlistUseCase,
    private val addToWishlistUseCase: AddToWishlistUseCase,
    private val removeWishlistUseCase: RemoveWishlistUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val foodId = savedStateHandle.get<Int>(FOOD_ID) ?: 0

    var shouldDisplayWishlistSnackbar by mutableStateOf(false)

    private val _foodDetailState = MutableStateFlow(FoodDetailState())
    val foodDetailState = _foodDetailState.asStateFlow()

    private val _wishlistState = MutableStateFlow(WishlistState())
    val wishlistState = _wishlistState.asStateFlow()

    private val _cartState = MutableStateFlow(CartState())
    val cartState = _cartState.asStateFlow()

    init {
        getFoodDetail(foodId)
        getWishlist(foodId)
    }

    private fun getFoodDetail(foodId: Int) {
        viewModelScope.launch {
            getFoodDetailUseCase.invoke(foodId).collect { result ->
                result.suspendSubscribe(
                    doOnLoading = {
                        _foodDetailState.update { state ->
                            state.copy(
                                isOffline = false,
                                isLoading = true
                            )
                        }
                    },
                    doOnSuccess = {
                        _foodDetailState.update { state ->
                            state.copy(
                                food = it.value,
                                isOffline = false,
                                isLoading = false,
                            )
                        }
                    },
                    doOnError = {
                        when (it.throwable) {
                            is ConnectivityException -> {
                                _foodDetailState.update { state ->
                                    state.copy(
                                        isOffline = true,
                                        isLoading = false,
                                        error = ""
                                    )
                                }
                            }

                            else -> {
                                _foodDetailState.update { state ->
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

    private fun getWishlist(foodId: Int) {
        viewModelScope.launch {
            checkWishlistUseCase.invoke(foodId).collect { result ->
                result.suspendSubscribe(
                    doOnSuccess = {
                        _wishlistState.update { state ->
                            state.copy(
                                isWishlist = it.value.orFalse(),
                                isLoading = false,
                            )
                        }
                    },
                    doOnError = {
                        _wishlistState.update { state ->
                            state.copy(
                                isLoading = false,
                                error = it.throwable?.message ?: "Unexpected error occurred"
                            )
                        }
                    }
                )
            }
        }
    }

    fun addToWishlist(food: Food?) {
        viewModelScope.launch {
            addToWishlistUseCase.invoke(food).collect { result ->
                result.suspendSubscribe(
                    doOnSuccess = {
                        shouldDisplayWishlistSnackbar = true
                        _wishlistState.update { state ->
                            state.copy(
                                isWishlist = true,
                                isLoading = false,
                            )
                        }
                    },
                    doOnError = {
                        _wishlistState.update { state ->
                            state.copy(
                                isLoading = false,
                                error = it.throwable?.message ?: "Unexpected error occurred"
                            )
                        }
                    }
                )
            }
        }
    }

    fun removeWishlist(foodId: Int?) {
        viewModelScope.launch {
            removeWishlistUseCase.invoke(foodId).collect { result ->
                result.suspendSubscribe(
                    doOnSuccess = {
                        shouldDisplayWishlistSnackbar = true
                        _wishlistState.update { state ->
                            state.copy(
                                isWishlist = false,
                                isLoading = false,
                            )
                        }
                    },
                    doOnError = {
                        _wishlistState.update { state ->
                            state.copy(
                                isLoading = false,
                                error = it.throwable?.message ?: "Unexpected error occurred"
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
                        _cartState.update { state ->
                            state.copy(
                                isLoading = true
                            )
                        }
                    },
                    doOnSuccess = {
                        _cartState.update { state ->
                            state.copy(
                                addToCart = it.value,
                                isLoading = false,
                            )
                        }
                    },
                    doOnError = {
                        _cartState.update { state ->
                            state.copy(
                                isLoading = false,
                                error = it.throwable?.message ?: "Unexpected error occurred"
                            )
                        }
                    }
                )
            }
        }
    }

    fun refresh() {
        getFoodDetail(foodId)
        getWishlist(foodId)
    }

    fun clearSnackbarState() {
        shouldDisplayWishlistSnackbar = false
    }

    fun resetErrorState() {
        _foodDetailState.update {
            it.copy(error = "")
        }
        _cartState.update {
            it.copy(error = "")
        }
        _wishlistState.update {
            it.copy(error = "")
        }
    }
}