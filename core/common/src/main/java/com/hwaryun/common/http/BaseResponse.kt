package com.hwaryun.common.http

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("meta")
    val meta: Meta? = null,
    @SerializedName("data")
    val data: T? = null
) {
    data class Meta(
        @SerializedName("code")
        val code: Int? = null,
        @SerializedName("message")
        val message: String? = null,
        @SerializedName("status")
        val status: String? = null
    )
}