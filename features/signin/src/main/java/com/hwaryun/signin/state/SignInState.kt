package com.hwaryun.signin.state

data class SignInState(
    val isLoading: Boolean = false,
    val signIn: Any? = null,
    val error: String = ""
)
