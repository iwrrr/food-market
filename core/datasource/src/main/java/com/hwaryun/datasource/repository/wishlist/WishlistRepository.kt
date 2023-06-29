package com.hwaryun.datasource.repository.wishlist

import com.hwaryun.common.result.DataResult
import com.hwaryun.database.model.WishlistEntity
import kotlinx.coroutines.flow.Flow

interface WishlistRepository {
    fun addToWishlist(wishlistEntity: WishlistEntity): Flow<DataResult<Unit>>

    fun getWishlist(foodId: Int, userId: Int): Flow<DataResult<WishlistEntity>>

    fun removeWishlist(foodId: Int, userId: Int): Flow<DataResult<Unit>>
}

