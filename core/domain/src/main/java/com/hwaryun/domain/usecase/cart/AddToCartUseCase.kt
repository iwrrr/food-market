package com.hwaryun.domain.usecase.cart

import com.hwaryun.common.CartAlreadyExistsException
import com.hwaryun.common.di.DispatcherProvider
import com.hwaryun.common.domain.FlowUseCase
import com.hwaryun.common.ext.suspendSubscribe
import com.hwaryun.common.result.UiResult
import com.hwaryun.datasource.repository.cart.CartRepository
import com.hwaryun.domain.mapper.toCartEntity
import com.hwaryun.domain.model.Food
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddToCartUseCase @Inject constructor(
    private val cartRepository: CartRepository,
    dispatcherProvider: DispatcherProvider
) : FlowUseCase<Food, UiResult<Unit>>(dispatcherProvider.io) {

    override fun buildFlowUseCase(param: Food?): Flow<UiResult<Unit>> = flow {
        emit(UiResult.Loading())
        param?.let { food ->
            cartRepository.getCartItems().collect { dataResult ->
                dataResult.suspendSubscribe(
                    doOnSuccess = {
                        if (dataResult.value.isNullOrEmpty()) {
                            val cartEntity = food.toCartEntity()
                            cartRepository.addToCart(cartEntity).collect { addToCartResult ->
                                addToCartResult.suspendSubscribe(
                                    doOnSuccess = {
                                        emit(UiResult.Success(Unit))
                                    },
                                    doOnError = {
                                        emit(UiResult.Failure(addToCartResult.throwable))
                                    }
                                )
                            }
                        } else {
                            emit(UiResult.Failure(CartAlreadyExistsException("Masih ada item nih di keranjang kamu!")))
                        }
                    },
                    doOnError = {
                        emit(UiResult.Failure(dataResult.throwable))
                    }
                )
            }
        }
    }
}