package com.hwaryun.network.model.response

import com.google.gson.annotations.SerializedName

data class FoodDto(
    @SerializedName("created_at")
    val createdAt: Long? = null,
    @SerializedName("deleted_at")
    val deletedAt: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("ingredients")
    val ingredients: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("picturePath")
    val picturePath: String? = null,
    @SerializedName("price")
    val price: String? = null,
    @SerializedName("rate")
    val rate: String? = null,
    @SerializedName("types")
    val types: String? = null,
    @SerializedName("updated_at")
    val updatedAt: Long? = null
)
