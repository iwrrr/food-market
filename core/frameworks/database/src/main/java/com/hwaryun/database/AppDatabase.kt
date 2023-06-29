package com.hwaryun.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hwaryun.database.dao.CartDao
import com.hwaryun.database.dao.WishlistDao
import com.hwaryun.database.model.CartEntity
import com.hwaryun.database.model.WishlistEntity

@Database(entities = [CartEntity::class, WishlistEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract val cartDao: CartDao

    abstract val wishlistDao: WishlistDao
}