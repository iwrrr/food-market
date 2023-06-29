package com.hwaryun.food_detail

data class CartState(
    val addToCart: Any? = null,
    val isLoading: Boolean = false,
    val error: String = ""
)