package com.hwaryun.domain.usecase

import com.hwaryun.common.di.DispatcherProvider
import com.hwaryun.common.domain.oop.FlowUseCase
import com.hwaryun.common.ext.suspendSubscribe
import com.hwaryun.common.result.UiResult
import com.hwaryun.datasource.datastore.UserPreferenceManager
import com.hwaryun.datasource.repository.AuthRepository
import com.hwaryun.domain.mapper.toViewParam
import com.hwaryun.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository,
    private val userPreferenceManager: UserPreferenceManager,
    private val checkLoginFieldUseCase: CheckLoginFieldUseCase,
    dispatcherProvider: DispatcherProvider
) : FlowUseCase<LoginUseCase.Param, UiResult<User>>(dispatcherProvider.io) {

    override suspend fun buildFlowUseCase(param: Param): Flow<UiResult<User>> = flow {
        emit(UiResult.Loading())
        checkLoginFieldUseCase.execute(param).first().suspendSubscribe(
            doOnSuccess = {
                repository.login(param.email, param.password).collect { result ->
                    result.suspendSubscribe(
                        doOnSuccess = {
                            result.value?.user?.let {
                                userPreferenceManager.saveUserToken(result.value?.accessToken)
                                userPreferenceManager.saveUser(it)
                                userPreferenceManager.user.collect { user ->
                                    emit(UiResult.Success(user.toViewParam()))
                                }
                            }
                        },
                        doOnError = {
                            emit(UiResult.Failure(it.throwable))
                        }
                    )
                }
            },
            doOnError = {
                emit(UiResult.Failure(it.throwable))
            }
        )
    }

    data class Param(
        val email: String,
        val password: String
    )
}