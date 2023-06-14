package com.hwaryun.domain.usecase.auth

import com.hwaryun.common.FieldErrorException
import com.hwaryun.common.di.DispatcherProvider
import com.hwaryun.common.domain.FlowUseCase
import com.hwaryun.common.result.CheckFieldResult
import com.hwaryun.common.result.UiResult
import com.hwaryun.designsystem.R
import com.hwaryun.domain.utils.ADDRESS_FIELD
import com.hwaryun.domain.utils.CITY_FIELD
import com.hwaryun.domain.utils.HOUSE_NUMBER_FIELD
import com.hwaryun.domain.utils.PHONE_NUMBER_FIELD
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CheckAddressFieldUseCase @Inject constructor(
    dispatcherProvider: DispatcherProvider
) : FlowUseCase<SignUpUseCase.Param, UiResult<CheckFieldResult>>(dispatcherProvider.io) {

    override suspend fun buildFlowUseCase(param: SignUpUseCase.Param?): Flow<UiResult<CheckFieldResult>> =
        flow {
            param?.let {
                val result = mutableListOf<Pair<Int, Int>>()
                checkPhoneNumberValid(param.phoneNumber)?.let {
                    result.add(it)
                }
                checkAddressValid(param.address)?.let {
                    result.add(it)
                }
                checkHouseNumberValid(param.houseNumber)?.let {
                    result.add(it)
                }
                checkCityValid(param.city)?.let {
                    result.add(it)
                }
                if (result.isEmpty()) {
                    emit(UiResult.Success(result))
                } else {
                    emit(UiResult.Failure(FieldErrorException(result)))
                }
            }
        }

    private fun checkPhoneNumberValid(value: String): Pair<Int, Int>? {
        return if (value.isEmpty()) {
            Pair(PHONE_NUMBER_FIELD, R.string.error_field_empty)
        } else {
            null
        }
    }

    private fun checkAddressValid(value: String): Pair<Int, Int>? {
        return if (value.isEmpty()) {
            Pair(ADDRESS_FIELD, R.string.error_field_empty)
        } else {
            null
        }
    }

    private fun checkHouseNumberValid(value: String): Pair<Int, Int>? {
        return if (value.isEmpty()) {
            Pair(HOUSE_NUMBER_FIELD, R.string.error_field_empty)
        } else {
            null
        }
    }

    private fun checkCityValid(value: String): Pair<Int, Int>? {
        return if (value.isEmpty()) {
            Pair(CITY_FIELD, R.string.error_field_empty)
        } else {
            null
        }
    }
}