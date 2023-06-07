package com.hwaryun.datasource.di

import com.hwaryun.datasource.datastore.UserPreferenceManager
import com.hwaryun.datasource.datastore.UserPreferenceManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataStoreModule {

    @Singleton
    @Binds
    abstract fun provideUserPreferenceManager(userPreferenceManagerImpl: UserPreferenceManagerImpl): UserPreferenceManager
}