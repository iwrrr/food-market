package com.hwaryun.network.model.request

import com.google.gson.annotations.SerializedName

data class CheckoutRequest(
    @SerializedName("food_id")
    val foodId: Int,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("quantity")
    val qty: Int,
    @SerializedName("total")
    val total: Int,
    @SerializedName("status")
    val status: String = "ON_DELIVERY",
)