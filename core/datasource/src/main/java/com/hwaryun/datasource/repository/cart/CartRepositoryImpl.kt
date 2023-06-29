package com.hwaryun.datasource.repository.cart

import com.hwaryun.common.ext.proceed
import com.hwaryun.common.result.DataResult
import com.hwaryun.database.dao.CartDao
import com.hwaryun.database.model.CartEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartDao: CartDao
) : CartRepository {

    override fun addToCart(cartEntity: CartEntity): Flow<DataResult<Unit>> = flow {
        emit(proceed { cartDao.addToCart(cartEntity) })
    }

    override fun getCartItems(): Flow<DataResult<List<CartEntity>>> = flow {
        emit(proceed { cartDao.getCartItems() })
    }

    override fun clearCart(): Flow<DataResult<Unit>> = flow {
        emit(proceed { cartDao.clearCart() })
    }
}