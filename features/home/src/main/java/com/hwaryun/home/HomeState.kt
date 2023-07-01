package com.hwaryun.home

import com.hwaryun.domain.model.Food
import com.hwaryun.domain.model.User

data class HomeState(
    val user: User? = null,
    val foods: List<Food> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = ""
)
