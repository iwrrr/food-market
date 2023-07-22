package com.hwaryun.datasource.repository.profile

import com.hwaryun.common.ext.execute
import com.hwaryun.common.http.BaseResponse
import com.hwaryun.common.result.DataResult
import com.hwaryun.network.FoodMarketApi
import com.hwaryun.network.model.request.UpdateProfileRequest
import com.hwaryun.network.model.response.AuthDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val foodMarketApi: FoodMarketApi
) : ProfileRepository {

    override suspend fun getUser(): Flow<DataResult<BaseResponse<AuthDto.UserDto>>> = flow {
        emit(execute { foodMarketApi.fetchUser() })
    }

    override suspend fun updatePhoto(file: File): Flow<DataResult<BaseResponse<List<String>>>> =
        flow {
            val image = MultipartBody.Part.createFormData(
                name = "file",
                filename = file.name,
                body = file.asRequestBody("image/*".toMediaType())
            )

            emit(execute { foodMarketApi.updatePhoto(image) })
        }

    override suspend fun updateProfile(
        name: String?,
        email: String?,
        password: String?,
        address: String?,
        phoneNumber: String?
    ): Flow<DataResult<BaseResponse<Unit>>> = flow {
        val body = UpdateProfileRequest(
            name = name,
            email = email,
            password = password,
            address = address,
            phoneNumber = phoneNumber,
        )

        emit(execute { foodMarketApi.updateProfile(body) })
    }
}