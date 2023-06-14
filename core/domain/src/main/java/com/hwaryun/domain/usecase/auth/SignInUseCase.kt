package com.hwaryun.domain.usecase.auth

import com.hwaryun.common.di.DispatcherProvider
import com.hwaryun.common.domain.FlowUseCase
import com.hwaryun.common.ext.suspendSubscribe
import com.hwaryun.common.result.UiResult
import com.hwaryun.datasource.datastore.UserPreferenceManager
import com.hwaryun.datasource.repository.AuthRepository
import com.hwaryun.domain.mapper.toUser
import com.hwaryun.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val repository: AuthRepository,
    private val userPreferenceManager: UserPreferenceManager,
    private val checkSignInFieldUseCase: CheckSignInFieldUseCase,
    dispatcherProvider: DispatcherProvider
) : FlowUseCase<SignInUseCase.Param, UiResult<User>>(dispatcherProvider.io) {

    override suspend fun buildFlowUseCase(param: Param?): Flow<UiResult<User>> = flow {
        emit(UiResult.Loading())
        param?.let {
            checkSignInFieldUseCase.execute(param).first().suspendSubscribe(
                doOnSuccess = {
                    repository.signIn(param.email, param.password).collect { result ->
                        result.suspendSubscribe(
                            doOnSuccess = {
                                result.value?.data?.let {
                                    userPreferenceManager.saveUserToken(it.accessToken)
                                    userPreferenceManager.saveUser(it.user)
                                    userPreferenceManager.user.collect { user ->
                                        emit(UiResult.Success(user.toUser()))
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
    }

    data class Param(
        val email: String,
        val password: String
    )
}