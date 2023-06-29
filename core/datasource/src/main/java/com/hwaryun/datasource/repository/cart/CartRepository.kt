package com.hwaryun.datasource.repository.cart

import com.hwaryun.common.result.DataResult
import com.hwaryun.database.model.CartEntity
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun addToCart(cartEntity: CartEntity): Flow<DataResult<Unit>>

    fun getCartItems(): Flow<DataResult<List<CartEntity>>>

    fun clearCart(): Flow<DataResult<Unit>>
}