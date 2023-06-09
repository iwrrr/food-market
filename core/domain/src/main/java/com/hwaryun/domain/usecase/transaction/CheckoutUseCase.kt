package com.hwaryun.domain.usecase.transaction

import com.hwaryun.common.di.DispatcherProvider
import com.hwaryun.common.domain.FlowUseCase
import com.hwaryun.common.ext.suspendSubscribe
import com.hwaryun.common.result.UiResult
import com.hwaryun.datasource.repository.cart.CartRepository
import com.hwaryun.datasource.repository.transaction.TransactionRepository
import com.hwaryun.domain.mapper.toTransaction
import com.hwaryun.domain.model.Transaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CheckoutUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val cartRepository: CartRepository,
    dispatcherProvider: DispatcherProvider
) : FlowUseCase<CheckoutUseCase.Param, UiResult<Transaction>>(dispatcherProvider.io) {

    override fun buildFlowUseCase(param: Param?): Flow<UiResult<Transaction>> = flow {
        emit(UiResult.Loading())
        param?.let { param ->
            transactionRepository.checkoutFood(
                foodId = param.foodId,
                userId = param.userId,
                qty = param.qty,
                total = param.total
            ).collect { result ->
                result.suspendSubscribe(
                    doOnSuccess = {
                        cartRepository.clearCart().collect { cartResult ->
                            cartResult.suspendSubscribe(
                                doOnSuccess = {
                                    result.value?.data?.let {
                                        emit(UiResult.Success(it.toTransaction()))
                                    }
                                },
                                doOnError = {
                                    emit(UiResult.Failure(it.throwable))
                                }
                            )
                        }
                    },
                    doOnError = {
                        emit(UiResult.Failure(it.throwable))
                    }
                )
            }
        }
    }

    data class Param(
        val foodId: Int,
        val userId: Int,
        val qty: Int,
        val total: Int,
    )
}