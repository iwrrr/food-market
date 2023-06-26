package com.hwaryun.payment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hwaryun.common.ext.orZero
import com.hwaryun.common.ext.subscribe
import com.hwaryun.datasource.datastore.UserPreferenceManager
import com.hwaryun.domain.mapper.toUser
import com.hwaryun.domain.usecase.cart.GetCartItemUseCase
import com.hwaryun.domain.usecase.payment.CheckoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getCartItemUseCase: GetCartItemUseCase,
    private val checkoutUseCase: CheckoutUseCase,
    private val userPreferenceManager: UserPreferenceManager
) : ViewModel() {

    private val _cartState = MutableStateFlow(CartState())
    val cartState = _cartState.asStateFlow()

    private val _transactionState = MutableStateFlow(TransactionState())
    val transactionState = _transactionState.asStateFlow()

    init {
        getUserData()
        getCartItem()
    }

    private fun getUserData() {
        viewModelScope.launch {
            val data = userPreferenceManager.user.first()
            _cartState.update {
                it.copy(user = data.toUser())
            }
        }
    }

    private fun getCartItem() {
        viewModelScope.launch {
            getCartItemUseCase.invoke().collect { result ->
                result.subscribe(
                    doOnLoading = {
                        _cartState.update { state ->
                            state.copy(isLoading = true)
                        }
                    },
                    doOnEmpty = {
                        _cartState.update { state ->
                            state.copy(
                                cart = null,
                                isLoading = false
                            )
                        }
                    },
                    doOnSuccess = {
                        _cartState.update { state ->
                            state.copy(
                                cart = it.value,
                                isLoading = false
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

    fun addQuantity(qty: Int) {
        var totalQty = qty
        if (totalQty >= 15) {
            _cartState.update {
                it.copy(qty = 15)
            }
        } else {
            totalQty++
            _cartState.update {
                it.copy(qty = totalQty)
            }
        }

        updateTotalPrice(totalQty)
    }

    fun reduceQuantity(qty: Int) {
        var totalQty = qty
        if (totalQty <= 1) {
            _cartState.update {
                it.copy(qty = 1)
            }
        } else {
            totalQty--
            _cartState.update {
                it.copy(qty = totalQty)
            }
        }

        updateTotalPrice(totalQty)
    }

    private fun updateTotalPrice(qty: Int) {
        _cartState.update {
            it.copy(
                totalFoodPrice = qty * (_cartState.value.cart?.price ?: 0)
            )
        }

        val shippingCost = _cartState.value.shippingCost
        val serviceFee = _cartState.value.serviceFee
        val totalFoodPrice = _cartState.value.totalFoodPrice

        _cartState.update {
            it.copy(
                totalPrice = totalFoodPrice + serviceFee + shippingCost
            )
        }
    }

    fun checkout() {
        viewModelScope.launch {
            checkoutUseCase.invoke(
                CheckoutUseCase.Param(
                    _cartState.value.cart?.id.orZero(),
                    _cartState.value.user?.id.orZero(),
                    _cartState.value.qty,
                    _cartState.value.totalPrice
                )
            ).collect { result ->
                result.subscribe(
                    doOnLoading = {
                        _transactionState.update { state ->
                            state.copy(isLoading = true)
                        }
                    },
                    doOnSuccess = {
                        _transactionState.update { state ->
                            state.copy(
                                transaction = it.value,
                                isLoading = false
                            )
                        }
                    },
                    doOnError = {
                        _transactionState.update { state ->
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

    //    fun cancelOrder() {
    //        viewModelScope.launch {
    //            cancelOrderUseCase.invoke(_cartState.value.transaction?.id).collect { result ->
    //                result.subscribe(
    //                    doOnLoading = {
    //                        _transactionState.update { state ->
    //                            state.copy(isLoading = true)
    //                        }
    //                    },
    //                    doOnSuccess = {
    //                        _transactionState.update { state ->
    //                            state.copy(
    //                                isCancelled = true,
    //                                isLoading = false
    //                            )
    //                        }
    //                    },
    //                    doOnError = {
    //                        _transactionState.update { state ->
    //                            state.copy(
    //                                isLoading = false,
    //                                error = it.throwable?.message ?: "Unexpected error accrued"
    //                            )
    //                        }
    //                    }
    //                )
    //            }
    //        }
    //    }
}