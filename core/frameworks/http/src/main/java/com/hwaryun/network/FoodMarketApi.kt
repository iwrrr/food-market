package com.hwaryun.network

import com.hwaryun.common.http.infrastructure.BaseResponse
import com.hwaryun.network.model.request.CheckoutRequest
import com.hwaryun.network.model.request.LoginRequest
import com.hwaryun.network.model.request.RegisterRequest
import com.hwaryun.network.model.response.AuthDto
import com.hwaryun.network.model.response.FoodDto
import com.hwaryun.network.model.response.TransactionDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface FoodMarketApi {

    @POST("login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): BaseResponse<AuthDto>

    @POST("register")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ): BaseResponse<AuthDto>

    @POST("logout")
    suspend fun logout(): BaseResponse<Boolean>

    @GET("food")
    suspend fun fetchFoods(
        @Query("page") page: Int? = null,
        @Query("limit") limit: Int? = null,
        @Query("types") types: String? = null
    ): BaseResponse<FoodDto>

    @GET("food")
    suspend fun fetchFoodById(
        @Query("id") id: Int
    ): BaseResponse<FoodDto.FoodItemDto>

    @POST("checkout")
    suspend fun checkout(
        @Body checkoutRequest: CheckoutRequest
    ): BaseResponse<TransactionDto>
}