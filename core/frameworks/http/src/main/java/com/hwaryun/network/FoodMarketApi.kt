package com.hwaryun.network

import com.hwaryun.common.http.infrastructure.BaseResponse
import com.hwaryun.network.model.request.CheckoutRequest
import com.hwaryun.network.model.request.LoginRequest
import com.hwaryun.network.model.request.RegisterRequest
import com.hwaryun.network.model.request.UpdateProfileRequest
import com.hwaryun.network.model.response.AuthDto
import com.hwaryun.network.model.response.FoodDto
import com.hwaryun.network.model.response.PagingDto
import com.hwaryun.network.model.response.TransactionDto
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
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
        @Query("name") name: String? = null,
        @Query("types") types: String? = null,
    ): BaseResponse<PagingDto<FoodDto>>

    @GET("food")
    suspend fun fetchFoodById(
        @Query("id") id: Int
    ): BaseResponse<FoodDto>

    @POST("checkout")
    suspend fun checkout(
        @Body checkoutRequest: CheckoutRequest
    ): BaseResponse<TransactionDto>

    @GET("transaction")
    suspend fun fetchTransactions(
        @Query("page") page: Int? = null,
        @Query("limit") limit: Int? = null,
        @Query("status", encoded = true) status: String? = null
    ): BaseResponse<PagingDto<TransactionDto>>

    @GET("transaction")
    suspend fun fetchTransactionById(
        @Query("id") id: Int
    ): BaseResponse<TransactionDto>

    @FormUrlEncoded
    @POST("transaction/{id}")
    suspend fun cancelOrder(
        @Path("id") id: Int,
        @Field("status") status: String
    ): BaseResponse<TransactionDto>

    @GET("user")
    suspend fun fetchUser(): BaseResponse<AuthDto.UserDto>

    @Multipart
    @POST("user/photo")
    suspend fun updatePhoto(
        @Part file: MultipartBody.Part
    ): BaseResponse<List<Unit>>

    @POST("user")
    suspend fun updateProfile(
        @Body updateProfileRequest: UpdateProfileRequest
    ): BaseResponse<Unit>
}