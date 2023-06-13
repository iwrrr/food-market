package com.hwaryun.domain.usecase.sign_in

import com.hwaryun.common.FieldErrorException
import com.hwaryun.common.di.DispatcherProvider
import com.hwaryun.common.domain.FlowUseCase
import com.hwaryun.common.ext.isValid
import com.hwaryun.common.result.CheckFieldResult
import com.hwaryun.common.result.UiResult
import com.hwaryun.designsystem.R
import com.hwaryun.domain.utils.EMAIL_FIELD
import com.hwaryun.domain.utils.PASSWORD_FIELD
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CheckSignInFieldUseCase @Inject constructor(
    dispatcherProvider: DispatcherProvider
) : FlowUseCase<SignInUseCase.Param, UiResult<CheckFieldResult>>(dispatcherProvider.io) {

    override suspend fun buildFlowUseCase(param: SignInUseCase.Param?): Flow<UiResult<CheckFieldResult>> =
        flow {
            param?.let {
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
        }

    private fun checkIsPasswordValid(password: String): Pair<Int, Int>? {
        return if (password.isEmpty()) {
            Pair(PASSWORD_FIELD, R.string.error_field_password)
        } else if (password.length < 8) {
            Pair(PASSWORD_FIELD, R.string.error_field_password_length_below_min)
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