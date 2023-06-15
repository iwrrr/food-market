package com.hwaryun.network.model.response

import com.google.gson.annotations.SerializedName

data class TransactionDto(
    @SerializedName("created_at")
    val createdAt: Long? = null,
    @SerializedName("deleted_at")
    val deletedAt: Long? = null,
    @SerializedName("food_id")
    val foodId: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("payment_url")
    val paymentUrl: String? = null,
    @SerializedName("quantity")
    val quantity: String? = null,
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("total")
    val total: String? = null,
    @SerializedName("updated_at")
    val updatedAt: Long? = null,
    @SerializedName("user_id")
    val userId: String? = null,
    @SerializedName("food")
    val food: FoodDto? = null,
    @SerializedName("user")
    val user: AuthDto.UserDto? = null,
)