package com.hwaryun.domain.usecase.profile

import com.hwaryun.common.di.DispatcherProvider
import com.hwaryun.common.domain.FlowUseCase
import com.hwaryun.common.ext.suspendSubscribe
import com.hwaryun.common.result.UiResult
import com.hwaryun.datasource.datastore.UserPreferenceManager
import com.hwaryun.datasource.repository.profile.ProfileRepository
import com.hwaryun.domain.mapper.toUser
import com.hwaryun.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val userPreferenceManager: UserPreferenceManager,
    dispatcherProvider: DispatcherProvider
) : FlowUseCase<Nothing, UiResult<User>>(dispatcherProvider.io) {

    override fun buildFlowUseCase(param: Nothing?): Flow<UiResult<User>> = flow {
        emit(UiResult.Loading())
        profileRepository.getUser().collect { result ->
            result.suspendSubscribe(
                doOnSuccess = {
                    result.value?.data?.let {
                        userPreferenceManager.saveUser(it)
                        emit(UiResult.Success(it.toUser()))
                    }
                },
                doOnError = {
                    Timber.e(it.throwable, "ERROR ====> ${it.throwable}")
                    emit(UiResult.Failure(it.throwable))
                }
            )
        }
    }
}