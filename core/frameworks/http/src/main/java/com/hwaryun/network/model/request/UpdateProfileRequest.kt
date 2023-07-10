package com.hwaryun.network.model.request

import com.google.gson.annotations.SerializedName

data class UpdateProfileRequest(
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("password")
    val password: String? = null,
    @SerializedName("address")
    val address: String? = null,
    @SerializedName("phoneNumber")
    val phoneNumber: String? = null,
)