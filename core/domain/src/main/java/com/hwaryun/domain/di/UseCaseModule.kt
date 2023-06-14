package com.hwaryun.domain.di

import com.hwaryun.common.di.DispatcherProvider
import com.hwaryun.datasource.datastore.UserPreferenceManager
import com.hwaryun.datasource.repository.AuthRepository
import com.hwaryun.datasource.repository.FoodRepository
import com.hwaryun.domain.usecase.food.GetFoodDetailUseCase
import com.hwaryun.domain.usecase.auth.LogoutUseCase
import com.hwaryun.domain.usecase.auth.CheckSignInFieldUseCase
import com.hwaryun.domain.usecase.auth.SignInUseCase
import com.hwaryun.domain.usecase.auth.CheckAddressFieldUseCase
import com.hwaryun.domain.usecase.auth.CheckSignUpFieldUseCase
import com.hwaryun.domain.usecase.auth.SignUpUseCase
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

    @Singleton
    @Provides
    fun provideLogoutUseCase(
        repository: AuthRepository,
        userPreferenceManager: UserPreferenceManager,
        dispatcherProvider: DispatcherProvider
    ): LogoutUseCase {
        return LogoutUseCase(
            repository,
            userPreferenceManager,
            dispatcherProvider
        )
    }

    @Singleton
    @Provides
    fun provideGetFoodDetailUseCase(
        foodRepository: FoodRepository,
        dispatcherProvider: DispatcherProvider
    ): GetFoodDetailUseCase {
        return GetFoodDetailUseCase(foodRepository, dispatcherProvider)
    }
}