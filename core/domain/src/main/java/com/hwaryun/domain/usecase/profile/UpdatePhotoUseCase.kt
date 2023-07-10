package com.hwaryun.domain.usecase.profile

import com.hwaryun.common.di.DispatcherProvider
import com.hwaryun.common.domain.FlowUseCase
import com.hwaryun.common.ext.suspendSubscribe
import com.hwaryun.common.result.UiResult
import com.hwaryun.datasource.repository.profile.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import java.io.File
import javax.inject.Inject

class UpdatePhotoUseCase @Inject constructor(
    private val profileRepository: ProfileRepository,
    dispatcherProvider: DispatcherProvider
) : FlowUseCase<File, UiResult<Unit>>(dispatcherProvider.io) {

    override fun buildFlowUseCase(param: File?): Flow<UiResult<Unit>> = flow {
        emit(UiResult.Loading())
        param?.let { file ->
            profileRepository.updatePhoto(file).collect { result ->
                result.suspendSubscribe(
                    doOnSuccess = {
                        emit(UiResult.Success(Unit))
                    },
                    doOnError = {
                        Timber.e(it.throwable, "ERROR ====> ${it.throwable}")
                        emit(UiResult.Failure(it.throwable))
                    }
                )
            }
        }
    }
}