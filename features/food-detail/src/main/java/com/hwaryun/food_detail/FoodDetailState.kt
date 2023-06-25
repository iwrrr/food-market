package com.hwaryun.food_detail

import com.hwaryun.domain.model.Food

data class FoodDetailState(
    val food: Food? = null,
    val qty: Int = 1,
    val totalPrice: Int = 0,
    val isLoading: Boolean = false,
    val error: String = ""
)
