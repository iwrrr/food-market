package com.hwaryun.login.state

data class LoginState(
    val signIn: Any? = null,
    val email: String = "",
    val password: String = "",
    val errorEmailMsg: Int = 0,
    val errorPasswordMsg: Int = 0,
    val isPasswordVisible: Boolean = false,
    val isEmailError: Boolean = false,
    val isPasswordError: Boolean = false,
    val isLoading: Boolean = false,
    val error: String = ""
)
