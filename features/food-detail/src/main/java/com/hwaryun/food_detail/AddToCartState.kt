package com.hwaryun.food_detail

data class AddToCartState(
    val addToCart: Any? = null,
    val isLoading: Boolean = false,
    val error: String = ""
)