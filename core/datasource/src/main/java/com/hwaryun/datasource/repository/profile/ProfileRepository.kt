package com.hwaryun.datasource.repository.profile

import com.hwaryun.common.http.infrastructure.BaseResponse
import com.hwaryun.common.result.DataResult
import com.hwaryun.network.model.response.AuthDto
import kotlinx.coroutines.flow.Flow
import java.io.File

interface ProfileRepository {
    suspend fun getUser(): Flow<DataResult<BaseResponse<AuthDto.UserDto>>>

    suspend fun updatePhoto(file: File): Flow<DataResult<BaseResponse<List<Unit>>>>

    suspend fun updateProfile(
        name: String?,
        email: String?,
        password: String?,
        address: String?,
        phoneNumber: String?,
    ): Flow<DataResult<BaseResponse<Unit>>>
}

