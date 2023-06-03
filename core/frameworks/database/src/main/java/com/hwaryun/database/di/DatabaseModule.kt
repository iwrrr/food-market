package com.hwaryun.database.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import com.hwaryun.database.AppDatabase
import com.hwaryun.database.BaseDao
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Provides
    fun provideFavoriteDao(database: AppDatabase): BaseDao<Unit> {
        return database.yourDao()
    }

    @Singleton
    @Provides
    fun provideRoomDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "databasename")
            .fallbackToDestructiveMigration()
            .build()
    }
}