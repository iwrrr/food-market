package com.hwaryun.domain.di

import com.hwaryun.common.di.DispatcherProvider
import com.hwaryun.datasource.datastore.UserPreferenceManager
import com.hwaryun.datasource.repository.AuthRepository
import com.hwaryun.domain.usecase.CheckLoginFieldUseCase
import com.hwaryun.domain.usecase.LoginUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun provideLoginUseCase(
        repository: AuthRepository,
        userPreferenceManager: UserPreferenceManager,
        checkLoginFieldUseCase: CheckLoginFieldUseCase,
        dispatcherProvider: DispatcherProvider
    ): LoginUseCase {
        return LoginUseCase(
            repository,
            userPreferenceManager,
            checkLoginFieldUseCase,
            dispatcherProvider
        )
    }

    @Singleton
    @Provides
    fun provideCheckLoginFieldUseCase(
        dispatcherProvider: DispatcherProvider
    ): CheckLoginFieldUseCase {
        return CheckLoginFieldUseCase(dispatcherProvider)
    }
}