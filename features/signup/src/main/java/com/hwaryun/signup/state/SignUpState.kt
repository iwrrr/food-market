package com.hwaryun.signup.state

data class SignUpState(
    val isLoading: Boolean = false,
    val signUp: Any? = null,
    val firstStep: Any? = null,
    val error: String = ""
)
