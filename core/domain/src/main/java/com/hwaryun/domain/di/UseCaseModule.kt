package com.hwaryun.domain.di

import com.hwaryun.common.di.DispatcherProvider
import com.hwaryun.datasource.datastore.UserPreferenceManager
import com.hwaryun.datasource.repository.AuthRepository
import com.hwaryun.domain.usecase.sign_in.CheckSignInFieldUseCase
import com.hwaryun.domain.usecase.sign_in.SignInUseCase
import com.hwaryun.domain.usecase.sign_up.CheckAddressFieldUseCase
import com.hwaryun.domain.usecase.sign_up.CheckSignUpFieldUseCase
import com.hwaryun.domain.usecase.sign_up.SignUpUseCase
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
    fun provideSignInUseCase(
        repository: AuthRepository,
        userPreferenceManager: UserPreferenceManager,
        checkSignInFieldUseCase: CheckSignInFieldUseCase,
        dispatcherProvider: DispatcherProvider
    ): SignInUseCase {
        return SignInUseCase(
            repository,
            userPreferenceManager,
            checkSignInFieldUseCase,
            dispatcherProvider
        )
    }

    @Singleton
    @Provides
    fun provideCheckSignInFieldUseCase(
        dispatcherProvider: DispatcherProvider
    ): CheckSignInFieldUseCase {
        return CheckSignInFieldUseCase(dispatcherProvider)
    }

    @Singleton
    @Provides
    fun provideSignUpUseCase(
        repository: AuthRepository,
        userPreferenceManager: UserPreferenceManager,
        checkAddressFieldUseCase: CheckAddressFieldUseCase,
        dispatcherProvider: DispatcherProvider
    ): SignUpUseCase {
        return SignUpUseCase(
            repository,
            userPreferenceManager,
            checkAddressFieldUseCase,
            dispatcherProvider
        )
    }

    @Singleton
    @Provides
    fun provideCheckSignUpFieldUseCase(
        dispatcherProvider: DispatcherProvider
    ): CheckSignUpFieldUseCase {
        return CheckSignUpFieldUseCase(dispatcherProvider)
    }

    @Singleton
    @Provides
    fun provideCheckAddressFieldUseCase(
        dispatcherProvider: DispatcherProvider
    ): CheckAddressFieldUseCase {
        return CheckAddressFieldUseCase(dispatcherProvider)
    }
}