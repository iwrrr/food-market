package com.hwaryun.profile

import com.hwaryun.domain.model.User

data class ProfileUiState(
    val user: User? = null,
    val logout: Any? = null,
    val isLoading: Boolean = false,
    val error: String = "",
)
