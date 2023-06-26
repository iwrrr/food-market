package com.hwaryun.domain.usecase.cart

import com.hwaryun.common.di.DispatcherProvider
import com.hwaryun.common.domain.FlowUseCase
import com.hwaryun.common.ext.suspendSubscribe
import com.hwaryun.common.result.UiResult
import com.hwaryun.datasource.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ClearCartUseCase @Inject constructor(
    private val cartRepository: CartRepository,
    dispatcherProvider: DispatcherProvider
) : FlowUseCase<Nothing, UiResult<Unit>>(dispatcherProvider.io) {

    override fun buildFlowUseCase(param: Nothing?): Flow<UiResult<Unit>> = flow {
        emit(UiResult.Loading())
        cartRepository.clearCart().collect { cartResult ->
            cartResult.suspendSubscribe(
                doOnSuccess = {
                    emit(UiResult.Success(Unit))
                },
                doOnError = {
                    emit(UiResult.Failure(it.throwable))
                }
            )
        }
    }
}