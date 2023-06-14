package com.hwaryun.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hwaryun.common.FieldErrorException
import com.hwaryun.common.ext.suspendSubscribe
import com.hwaryun.domain.usecase.auth.CheckSignUpFieldUseCase
import com.hwaryun.domain.usecase.auth.SignUpUseCase
import com.hwaryun.domain.utils.ADDRESS_FIELD
import com.hwaryun.domain.utils.CITY_FIELD
import com.hwaryun.domain.utils.EMAIL_FIELD
import com.hwaryun.domain.utils.HOUSE_NUMBER_FIELD
import com.hwaryun.domain.utils.NAME_FIELD
import com.hwaryun.domain.utils.PASSWORD_FIELD
import com.hwaryun.domain.utils.PHONE_NUMBER_FIELD
import com.hwaryun.signup.state.SignUpState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val checkSignUpFieldUseCase: CheckSignUpFieldUseCase,
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {

    private val _signUpState = MutableStateFlow(SignUpState())
    val signUpState = _signUpState.asStateFlow()

    fun validateFirstStep(
        name: String,
        email: String,
        password: String,
    ) {
        viewModelScope.launch {
            checkSignUpFieldUseCase.execute(
                CheckSignUpFieldUseCase.Param(
                    name,
                    email,
                    password
                )
            ).collect { result ->
                result.suspendSubscribe(
                    doOnSuccess = {
                        _signUpState.update {
                            it.copy(
                                firstStep = result.value
                            )
                        }
                    },
                    doOnError = {
                        if (it.throwable is FieldErrorException) {
                            handleFieldError(it.throwable as FieldErrorException)
                        }
                    }
                )
            }
        }
    }

    fun signUp() {
        viewModelScope.launch {
            signUpUseCase.execute(
                SignUpUseCase.Param(
                    name = _signUpState.value.name,
                    email = _signUpState.value.email,
                    password = _signUpState.value.password,
                    address = _signUpState.value.address,
                    city = _signUpState.value.city,
                    houseNumber = _signUpState.value.houseNumber,
                    phoneNumber = _signUpState.value.phoneNumber,
                )
            ).collect { result ->
                result.suspendSubscribe(
                    doOnLoading = {
                        _signUpState.update {
                            it.copy(
                                isLoading = true
                            )
                        }
                    },
                    doOnSuccess = {
                        _signUpState.update {
                            it.copy(
                                isLoading = false,
                                signUp = result.value
                            )
                        }
                    },
                    doOnError = {
                        if (it.throwable is FieldErrorException) {
                            handleFieldError(it.throwable as FieldErrorException)
                        } else {
                            _signUpState.update { state ->
                                state.copy(
                                    isLoading = false,
                                    error = result.throwable?.message ?: "Unexpected error accrued"
                                )
                            }
                        }
                    }
                )
            }
        }
    }

    fun updateNameState(value: String) {
        _signUpState.update {
            it.copy(
                name = value,
                isNameError = false,
            )
        }
    }

    fun updateEmailState(value: String) {
        _signUpState.update {
            it.copy(
                email = value.trim(),
                isEmailError = false,
            )
        }
    }

    fun updatePasswordState(value: String) {
        _signUpState.update {
            it.copy(
                password = value.trim(),
                isPasswordError = false,
            )
        }
    }

    fun updatePhoneNumberState(value: String) {
        _signUpState.update {
            it.copy(
                phoneNumber = value.trim(),
                isPhoneNumberError = false,
            )
        }
    }

    fun updateAddressState(value: String) {
        _signUpState.update {
            it.copy(
                address = value,
                isAddressError = false,
            )
        }
    }

    fun updateHouseNumberState(value: String) {
        _signUpState.update {
            it.copy(
                houseNumber = value,
                isHouseNumberError = false,
            )
        }
    }

    fun updateCityState(value: String) {
        _signUpState.update {
            it.copy(
                city = value,
                isCityError = false,
            )
        }
    }

    fun updateIsPasswordVisible(value: Boolean) {
        _signUpState.update {
            it.copy(
                isPasswordVisible = value
            )
        }
    }

    private fun handleFieldError(exception: FieldErrorException) {
        exception.errorFields.forEach { errorField ->
            if (errorField.first == NAME_FIELD) {
                _signUpState.update {
                    it.copy(
                        errorNameMsg = errorField.second,
                        isNameError = true,
                        isLoading = false,
                    )
                }
            }
            if (errorField.first == EMAIL_FIELD) {
                _signUpState.update {
                    it.copy(
                        errorEmailMsg = errorField.second,
                        isEmailError = true,
                        isLoading = false,
                    )
                }
            }
            if (errorField.first == PASSWORD_FIELD) {
                _signUpState.update {
                    it.copy(
                        errorPasswordMsg = errorField.second,
                        isPasswordError = true,
                        isLoading = false,
                    )
                }
            }
            if (errorField.first == PHONE_NUMBER_FIELD) {
                _signUpState.update {
                    it.copy(
                        errorPhoneNumberMsg = errorField.second,
                        isPhoneNumberError = true,
                        isLoading = false,
                    )
                }
            }
            if (errorField.first == ADDRESS_FIELD) {
                _signUpState.update {
                    it.copy(
                        errorAddressMsg = errorField.second,
                        isAddressError = true,
                        isLoading = false,
                    )
                }
            }
            if (errorField.first == HOUSE_NUMBER_FIELD) {
                _signUpState.update {
                    it.copy(
                        errorHouseNumberMsg = errorField.second,
                        isHouseNumberError = true,
                        isLoading = false,
                    )
                }
            }
            if (errorField.first == CITY_FIELD) {
                _signUpState.update {
                    it.copy(
                        errorCityMsg = errorField.second,
                        isCityError = true,
                        isLoading = false,
                    )
                }
            }
        }
    }
}