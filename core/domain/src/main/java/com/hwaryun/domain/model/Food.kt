package com.hwaryun.domain.model

data class Food(
    val description: String,
    val id: Int,
    val ingredients: String,
    val name: String,
    val picturePath: String,
    val price: Int,
    val rate: Float,
    val types: String,
    val deliveryTime: Int,
    var isWishlist: Boolean = false,
)
