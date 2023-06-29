package com.hwaryun.food_detail

data class WishlistState(
    val isWishlist: Boolean = false,
    val isLoading: Boolean = false,
    val error: String = ""
)