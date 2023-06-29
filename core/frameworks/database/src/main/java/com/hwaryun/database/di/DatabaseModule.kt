package com.hwaryun.database.di

import android.content.Context
import androidx.room.Room
import com.hwaryun.database.AppDatabase
import com.hwaryun.database.dao.CartDao
import com.hwaryun.database.dao.WishlistDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideCartDao(database: AppDatabase): CartDao {
        return database.cartDao
    }

    @Singleton
    @Provides
    fun provideWishlistDao(database: AppDatabase): WishlistDao {
        return database.wishlistDao
    }

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "food_market.db")
            .fallbackToDestructiveMigration()
            .build()
    }
}