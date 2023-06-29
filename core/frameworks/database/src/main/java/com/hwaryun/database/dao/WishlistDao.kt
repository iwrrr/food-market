package com.hwaryun.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hwaryun.database.model.WishlistEntity

@Dao
interface WishlistDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToWishlist(wishlistEntity: WishlistEntity)

    @Query("SELECT * FROM wishlists WHERE foodId = :foodId AND userId = :userId")
    suspend fun getWishlist(foodId: Int, userId: Int): WishlistEntity

    @Query("DELETE FROM wishlists WHERE foodId = :foodId AND userId = :userId")
    suspend fun removeWishlist(foodId: Int, userId: Int)
}