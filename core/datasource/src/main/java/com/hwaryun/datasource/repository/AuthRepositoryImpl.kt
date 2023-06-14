package com.hwaryun.datasource.repository

import com.hwaryun.common.http.infrastructure.BaseResponse
import com.hwaryun.common.http.infrastructure.execute
import com.hwaryun.common.result.NetworkClientResult
import com.hwaryun.network.FoodMarketApi
import com.hwaryun.network.model.request.LoginRequest
import com.hwaryun.network.model.request.RegisterRequest
import com.hwaryun.network.model.response.AuthDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val foodMarketApi: FoodMarketApi
) : AuthRepository {

    override suspend fun signIn(
        email: String,
        password: String
    ): Flow<NetworkClientResult<BaseResponse<AuthDto>>> = flow {
        val loginRequest = LoginRequest(
            email = email,
            password = password
        )

        emit(execute { foodMarketApi.login(loginRequest) })
    }

    override suspend fun signUp(
        name: String,
        email: String,
        password: String,
        address: String,
        city: String,
        houseNumber: String,
        phoneNumber: String
    ): Flow<NetworkClientResult<BaseResponse<AuthDto>>> = flow {
        val registerRequest = RegisterRequest(
            name = name,
            email = email,
            password = password,
            passwordConfirmation = password,
            address = address,
            city = city,
            houseNumber = houseNumber,
            phoneNumber = phoneNumber
        )

        emit(execute { foodMarketApi.register(registerRequest) })
    }

    override suspend fun logout(): Flow<NetworkClientResult<Boolean?>> = flow {
        emit(execute { foodMarketApi.logout().data })
    }
}