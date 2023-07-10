package com.hwaryun.edit_profile

import com.hwaryun.domain.model.User

data class EditProfileState(
    val user: User? = null,
    val updatePhoto: Any? = null,
    val updateProfile: Any? = null,
    val name: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val errorNameMsg: Int = 0,
    val errorEmailMsg: Int = 0,
    val errorPhoneNumberMsg: Int = 0,
    val isNameError: Boolean = false,
    val isEmailError: Boolean = false,
    val isPhoneNumberError: Boolean = false,
    val isLoading: Boolean = false,
    val error: String = "",
)
