package com.hwaryun.transaction_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hwaryun.common.ext.subscribe
import com.hwaryun.domain.usecase.payment.CancelOrderUseCase
import com.hwaryun.domain.usecase.payment.GetTransactionDetailUseCase
import com.hwaryun.transaction_detail.navigation.TRANSACTION_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionDetailViewModel @Inject constructor(
    private val getTransactionDetailUseCase: GetTransactionDetailUseCase,
    private val cancelOrderUseCase: CancelOrderUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _transactionState = MutableStateFlow(TransactionState())
    val transactionState = _transactionState.asStateFlow()

    private val _cancelOrderState = MutableStateFlow(CancelOrderState())
    val cancelOrderState = _cancelOrderState.asStateFlow()

    init {
        val transactionId = savedStateHandle.get<Int>(TRANSACTION_ID) ?: 0
        getTransactionDetail(transactionId)
    }

    private fun getTransactionDetail(transactionId: Int) {
        viewModelScope.launch {
            getTransactionDetailUseCase.invoke(transactionId).collect { result ->
                result.subscribe(
                    doOnLoading = {
                        _transactionState.update { state ->
                            state.copy(isLoading = true)
                        }
                    },
                    doOnSuccess = {
                        _transactionState.update { state ->
                            state.copy(
                                transaction = result.value,
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
                    },
                )
            }
        }
    }

    fun cancelOrder() {
        viewModelScope.launch {
            cancelOrderUseCase.invoke(_transactionState.value.transaction?.id).collect { result ->
                result.subscribe(
                    doOnLoading = {
                        _cancelOrderState.update { state ->
                            state.copy(isLoading = true)
                        }
                    },
                    doOnSuccess = {
                        _cancelOrderState.update { state ->
                            state.copy(
                                isCancelled = true,
                                isLoading = false
                            )
                        }
                    },
                    doOnError = {
                        _cancelOrderState.update { state ->
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
        _transactionState.update {
            it.copy(error = "")
        }
        _cancelOrderState.update {
            it.copy(error = "")
        }
    }
}