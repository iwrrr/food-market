package com.hwaryun.food_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hwaryun.common.ext.orFalse
import com.hwaryun.common.ext.suspendSubscribe
import com.hwaryun.datasource.util.NetworkMonitor
import com.hwaryun.domain.model.Food
import com.hwaryun.domain.usecase.cart.AddToCartUseCase
import com.hwaryun.domain.usecase.food.GetFoodDetailUseCase
import com.hwaryun.domain.usecase.wishlist.AddToWishlistUseCase
import com.hwaryun.domain.usecase.wishlist.CheckWishlistUseCase
import com.hwaryun.domain.usecase.wishlist.RemoveWishlistUseCase
import com.hwaryun.food_detail.navigation.FOOD_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
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
    networkMonitor: NetworkMonitor,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val foodId = savedStateHandle.get<Int>(FOOD_ID) ?: 0

    private val _foodDetailState = MutableStateFlow(FoodDetailState())
    val foodDetailState = _foodDetailState.asStateFlow()

    private val _wishlistState = MutableStateFlow(WishlistState())
    val wishlistState = _wishlistState.asStateFlow()

    private val _cartState = MutableStateFlow(CartState())
    val cartState = _cartState.asStateFlow()

    val isOffline = networkMonitor.isOnline
        .map(Boolean::not)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false
        )

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
                                error = it.throwable?.message ?: "Unexpected error accrued"
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
                                error = it.throwable?.message ?: "Unexpected error accrued"
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
                                error = it.throwable?.message ?: "Unexpected error accrued"
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