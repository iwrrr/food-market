package com.hwaryun.domain.usecase.profile

import com.hwaryun.common.di.DispatcherProvider
import com.hwaryun.common.domain.FlowUseCase
import com.hwaryun.common.ext.suspendSubscribe
import com.hwaryun.common.result.UiResult
import com.hwaryun.datasource.repository.profile.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository,
    dispatcherProvider: DispatcherProvider
) : FlowUseCase<UpdateProfileUseCase.Param, UiResult<Unit>>(dispatcherProvider.io) {

    override fun buildFlowUseCase(param: Param?): Flow<UiResult<Unit>> = flow {
        emit(UiResult.Loading())
        param?.let {
            profileRepository.updateProfile(
                name = param.name,
                email = param.email,
                password = param.password,
                address = param.address,
                phoneNumber = param.phoneNumber
            ).collect { result ->
                result.suspendSubscribe(
                    doOnSuccess = {
                        emit(UiResult.Success(Unit))
                    },
                    doOnError = {
                        emit(UiResult.Failure(it.throwable))
                    }
                )
            }
        }
    }

    data class Param(
        val name: String? = null,
        val email: String? = null,
        val password: String? = null,
        val address: String? = null,
        val phoneNumber: String? = null,
    )
}