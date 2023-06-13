package com.hwaryun.home

import com.hwaryun.domain.model.User

data class HomeState(
    val user: User? = null,
    val isLoading: Boolean = false,
    val error: String = ""
)
