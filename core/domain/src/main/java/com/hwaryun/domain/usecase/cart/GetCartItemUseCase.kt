package com.hwaryun.domain.usecase.cart

import com.hwaryun.common.di.DispatcherProvider
import com.hwaryun.common.domain.FlowUseCase
import com.hwaryun.common.ext.suspendSubscribe
import com.hwaryun.common.result.UiResult
import com.hwaryun.datasource.repository.CartRepository
import com.hwaryun.domain.mapper.toCart
import com.hwaryun.domain.model.Cart
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCartItemUseCase @Inject constructor(
    private val cartRepository: CartRepository,
    dispatcherProvider: DispatcherProvider
) : FlowUseCase<Nothing, UiResult<Cart>>(dispatcherProvider.io) {

    override fun buildFlowUseCase(param: Nothing?): Flow<UiResult<Cart>> = flow {
        emit(UiResult.Loading())
        cartRepository.getCartItems().collect { dataResult ->
            dataResult.suspendSubscribe(
                doOnSuccess = {
                    dataResult.value?.let { carts ->
                        if (carts.isNotEmpty()) {
                            emit(UiResult.Success(carts.first().toCart()))
                        }
                    }
                },
                doOnError = {
                    emit(UiResult.Failure(dataResult.throwable))
                }
            )
        }
    }
}