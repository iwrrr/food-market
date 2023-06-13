package com.hwaryun.datasource.di

import com.hwaryun.datasource.repository.AuthRepository
import com.hwaryun.datasource.repository.AuthRepositoryImpl
import com.hwaryun.datasource.repository.FoodRepository
import com.hwaryun.datasource.repository.FoodRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Singleton
    @Binds
    abstract fun bindFoodRepository(foodRepositoryImpl: FoodRepositoryImpl): FoodRepository
}