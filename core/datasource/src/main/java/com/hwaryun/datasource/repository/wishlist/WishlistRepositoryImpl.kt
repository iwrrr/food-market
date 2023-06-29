package com.hwaryun.datasource.repository.wishlist

import com.hwaryun.common.ext.proceed
import com.hwaryun.common.result.DataResult
import com.hwaryun.database.dao.WishlistDao
import com.hwaryun.database.model.WishlistEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WishlistRepositoryImpl @Inject constructor(
    private val wishlistDao: WishlistDao
) : WishlistRepository {

    override fun addToWishlist(wishlistEntity: WishlistEntity): Flow<DataResult<Unit>> = flow {
        emit(proceed { wishlistDao.addToWishlist(wishlistEntity) })
    }

    override fun getWishlist(foodId: Int, userId: Int): Flow<DataResult<WishlistEntity>> = flow {
        emit(proceed { wishlistDao.getWishlist(foodId, userId) })
    }

    override fun removeWishlist(foodId: Int, userId: Int): Flow<DataResult<Unit>> = flow {
        emit(proceed { wishlistDao.removeWishlist(foodId, userId) })
    }
}