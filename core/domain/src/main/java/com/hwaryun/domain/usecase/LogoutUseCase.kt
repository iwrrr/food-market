package com.hwaryun.domain.usecase

import com.hwaryun.common.di.DispatcherProvider
import com.hwaryun.common.domain.FlowUseCase
import com.hwaryun.common.ext.suspendSubscribe
import com.hwaryun.common.result.UiResult
import com.hwaryun.datasource.datastore.UserPreferenceManager
import com.hwaryun.datasource.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val userPreferenceManager: UserPreferenceManager,
    dispatcherProvider: DispatcherProvider
) : FlowUseCase<Nothing, UiResult<Unit>>(dispatcherProvider.io) {

    override suspend fun buildFlowUseCase(param: Nothing?): Flow<UiResult<Unit>> = flow {
        emit(UiResult.Loading())
        authRepository.logout().collect { result ->
            result.suspendSubscribe(
                doOnSuccess = {
                    userPreferenceManager.clearUser()
                    emit(UiResult.Success(Unit))
                },
                doOnError = {
                    emit(UiResult.Failure(it.throwable))
                }
            )
        }
    }
}