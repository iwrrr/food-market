package com.hwaryun.home

import com.hwaryun.domain.model.Food

data class HomeState(
    val foods: List<Food> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = ""
)
