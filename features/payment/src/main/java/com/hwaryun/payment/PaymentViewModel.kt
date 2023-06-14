package com.hwaryun.payment

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hwaryun.common.ext.orZero
import com.hwaryun.common.ext.subscribe
import com.hwaryun.common.ext.suspendSubscribe
import com.hwaryun.datasource.datastore.UserPreferenceManager
import com.hwaryun.domain.mapper.toUser
import com.hwaryun.domain.usecase.food.GetFoodDetailUseCase
import com.hwaryun.domain.usecase.payment.CheckoutUseCase
import com.hwaryun.payment.navigation.FOOD_ID
import com.hwaryun.payment.navigation.QTY
import com.hwaryun.payment.navigation.TOTAL
import com.hwaryun.payment.navigation.TRANSACTION_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val getFoodDetailUseCase: GetFoodDetailUseCase,
    private val checkoutUseCase: CheckoutUseCase,
    private val userPreferenceManager: UserPreferenceManager,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _paymentUiState = MutableStateFlow(PaymentUiState())
    val paymentUiState = _paymentUiState.asStateFlow()

    private val _transactionUiState = MutableStateFlow(TransactionUiState())
    val transactionUiState = _transactionUiState.asStateFlow()

    private val transactionId: Int = (savedStateHandle.get<String>(TRANSACTION_ID) ?: "0").toInt()
    private val foodId: Int = (savedStateHandle.get<String>(FOOD_ID) ?: "0").toInt()
    private var qty: Int = (savedStateHandle.get<String>(QTY) ?: "0").toInt()
    private var totalFoodPrice: Int = (savedStateHandle.get<String>(TOTAL) ?: "0").toInt()

    init {
        if (transactionId != 0) {
            // TODO: Integrate order feature
        } else {
            getFoodDetail(foodId)
        }

        getUserData()
    }

    private fun getFoodDetail(foodId: Int) {
        viewModelScope.launch {
            getFoodDetailUseCase.execute(foodId).collect { result ->
                result.suspendSubscribe(
                    doOnLoading = {
                        _paymentUiState.update { state ->
                            state.copy(isLoading = true)
                        }
                    },
                    doOnSuccess = {
                        val shippingCost = 10000
                        val tax = totalFoodPrice.div(10)
                        val totalPrice = totalFoodPrice + shippingCost + tax
                        _paymentUiState.update { state ->
                            state.copy(
                                food = it.value,
                                isLoading = false,
                                qty = qty,
                                tax = tax,
                                shippingCost = shippingCost,
                                totalFoodPrice = totalFoodPrice,
                                totalPrice = totalPrice
                            )
                        }
                    },
                    doOnError = {
                        _paymentUiState.update { state ->
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

    private fun getUserData() {
        viewModelScope.launch {
            val data = userPreferenceManager.user.first()
            if (data != null) {
                _paymentUiState.update {
                    it.copy(user = data.toUser())
                }
            }
        }
    }

    fun checkout() {
        viewModelScope.launch {
            checkoutUseCase.execute(
                CheckoutUseCase.Param(
                    _paymentUiState.value.food?.id.orZero(),
                    _paymentUiState.value.user?.id.orZero(),
                    _paymentUiState.value.qty,
                    _paymentUiState.value.totalPrice
                )
            ).collect { result ->
                result.subscribe(
                    doOnLoading = {
                        _transactionUiState.update { state ->
                            state.copy(isLoading = true)
                        }
                    },
                    doOnSuccess = {
                        _transactionUiState.update { state ->
                            state.copy(
                                transaction = it.value,
                                isLoading = false
                            )
                        }
                    },
                    doOnError = {
                        _transactionUiState.update { state ->
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
}