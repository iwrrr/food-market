package com.hwaryun.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hwaryun.database.model.CartEntity

@Database(entities = [CartEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract val cartDao: CartDao
}