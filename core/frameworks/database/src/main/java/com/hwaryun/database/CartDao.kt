package com.hwaryun.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hwaryun.database.model.CartEntity

@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToCart(cartEntity: CartEntity)

    @Query("SELECT * FROM carts")
    suspend fun getCartItems(): List<CartEntity>

    @Query("DELETE FROM carts")
    suspend fun clearCart()
}