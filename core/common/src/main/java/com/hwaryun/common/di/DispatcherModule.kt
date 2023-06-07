package com.hwaryun.common.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DispatcherModule {
    @Singleton
    @Binds
    abstract fun provideDispatcherProvider(dispatcherProviderImpl: DispatcherProviderImpl): DispatcherProvider
}