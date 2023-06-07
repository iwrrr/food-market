package com.hwaryun.signin.state

data class SignInScreenState(
    val email: String = "",
    val password: String = "",
    val errorEmail: Int = 0,
    val errorPassword: Int = 0,
    val isPasswordVisible: Boolean = false,
    val isEmailError: Boolean = false,
    val isPasswordError: Boolean = false,
    val isLoading: Boolean = false,
)
