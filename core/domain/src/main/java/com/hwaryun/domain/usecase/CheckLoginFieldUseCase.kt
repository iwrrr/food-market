package com.hwaryun.domain.usecase

import com.hwaryun.common.FieldErrorException
import com.hwaryun.common.di.DispatcherProvider
import com.hwaryun.common.domain.oop.FlowUseCase
import com.hwaryun.common.ext.isValid
import com.hwaryun.common.result.UiResult
import com.hwaryun.designsystem.R
import com.hwaryun.domain.utils.EMAIL_FIELD
import com.hwaryun.domain.utils.PASSWORD_FIELD
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

typealias CheckFieldLoginResult = List<Pair<Int, Int>>

class CheckLoginFieldUseCase @Inject constructor(
    dispatcherProvider: DispatcherProvider
) : FlowUseCase<LoginUseCase.Param, UiResult<CheckFieldLoginResult>>(dispatcherProvider.io) {

    override suspend fun buildFlowUseCase(param: LoginUseCase.Param): Flow<UiResult<CheckFieldLoginResult>> =
        flow {
            val result = mutableListOf<Pair<Int, Int>>()
            checkIsEmailValid(param.email)?.let {
                result.add(it)
            }
            checkIsPasswordValid(param.password)?.let {
                result.add(it)
            }
            if (result.isEmpty()) {
                emit(UiResult.Success(result))
            } else {
                emit(UiResult.Failure(FieldErrorException(result)))
            }
        }

    private fun checkIsPasswordValid(password: String): Pair<Int, Int>? {
        return if (password.isEmpty()) {
            Pair(PASSWORD_FIELD, R.string.error_field_password)
        } else {
            null
        }
    }

    private fun checkIsEmailValid(email: String): Pair<Int, Int>? {
        return if (email.isEmpty()) {
            Pair(EMAIL_FIELD, R.string.error_field_email)
        } else if (!email.isValid) {
            Pair(EMAIL_FIELD, R.string.error_field_email_not_valid)
        } else {
            null
        }
    }
}