package com.hwaryun.signin.state

data class SignInScreenState(
    val email: String = "",
    val password: String = "",
    val errorEmailMsg: Int = 0,
    val errorPasswordMsg: Int = 0,
    val isPasswordVisible: Boolean = false,
    val isEmailError: Boolean = false,
    val isPasswordError: Boolean = false,
)
