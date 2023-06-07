package com.hwaryun.network

import com.hwaryun.common.BaseResponse
import com.hwaryun.network.model.request.LoginRequest
import com.hwaryun.network.model.request.RegisterRequest
import com.hwaryun.network.model.response.AuthDto
import retrofit2.http.Body
import retrofit2.http.POST

interface FoodMarketApi {

    @POST("login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): BaseResponse<AuthDto>

    @POST("register")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ): BaseResponse<AuthDto>
}