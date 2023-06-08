package com.hwaryun.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hwaryun.common.FieldErrorException
import com.hwaryun.common.ext.suspendSubscribe
import com.hwaryun.domain.usecase.sign_up.CheckSignUpFieldUseCase
import com.hwaryun.domain.usecase.sign_up.SignUpUseCase
import com.hwaryun.domain.utils.ADDRESS_FIELD
import com.hwaryun.domain.utils.CITY_FIELD
import com.hwaryun.domain.utils.EMAIL_FIELD
import com.hwaryun.domain.utils.HOUSE_NUMBER_FIELD
import com.hwaryun.domain.utils.NAME_FIELD
import com.hwaryun.domain.utils.PASSWORD_FIELD
import com.hwaryun.domain.utils.PHONE_NUMBER_FIELD
import com.hwaryun.signup.state.SignUpScreenState
import com.hwaryun.signup.state.SignUpState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val checkSignUpFieldUseCase: CheckSignUpFieldUseCase,
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {

    private val _signUpState = MutableStateFlow(SignUpState())
    val signUpState = _signUpState.asStateFlow()

    private val _screenState = MutableStateFlow(SignUpScreenState())
    val screenState = _screenState.asStateFlow()

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
            ).collect {result ->
                result.suspendSubscribe(
                    doOnSuccess = {
                        _signUpState.emit(
                            SignUpState(
                                firstStep = result.value
                            )
                        )
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
                    name = _screenState.value.name,
                    email = _screenState.value.email,
                    password = _screenState.value.password,
                    address = _screenState.value.address,
                    city = _screenState.value.city,
                    houseNumber = _screenState.value.houseNumber,
                    phoneNumber = _screenState.value.phoneNumber,
                )
            ).collect { result ->
                result.suspendSubscribe(
                    doOnLoading = {
                        _signUpState.emit(
                            SignUpState(
                                isLoading = true
                            )
                        )
                        Timber.d("DEBUG ====> ${result.value}")
                    },
                    doOnSuccess = {
                        _signUpState.emit(
                            SignUpState(
                                signUp = result.value
                            )
                        )
                        Timber.d("DEBUG ====> ${result.value}")
                        updateIsLoadingState(isLoading = false)
                    },
                    doOnError = {
                        if (it.throwable is FieldErrorException) {
                            handleFieldError(it.throwable as FieldErrorException)
                        } else {
                            _signUpState.emit(
                                SignUpState(
                                    error = result.throwable?.message ?: "Unexpected error accrued"
                                )
                            )
                        }
                        Timber.d("DEBUG ====> ${result.throwable}")
                        updateIsLoadingState(isLoading = false)
                    }
                )
            }
        }
    }

    fun updateNameState(value: String) {
        _screenState.update {
            it.copy(
                name = value,
                isNameError = false
            )
        }
    }

    fun updateEmailState(value: String) {
        _screenState.update {
            it.copy(
                email = value.trim(),
                isEmailError = false
            )
        }
    }

    fun updatePasswordState(value: String) {
        _screenState.update {
            it.copy(
                password = value.trim(),
                isPasswordError = false
            )
        }
    }

    fun updatePhoneNumberState(value: String) {
        _screenState.update {
            it.copy(
                phoneNumber = value.trim(),
                isPhoneNumberError = false
            )
        }
    }

    fun updateAddressState(value: String) {
        _screenState.update {
            it.copy(
                address = value.trim(),
                isAddressError = false
            )
        }
    }

    fun updateHouseNumberState(value: String) {
        _screenState.update {
            it.copy(
                houseNumber = value.trim(),
                isHouseNumberError = false
            )
        }
    }

    fun updateCityState(value: String) {
        _screenState.update {
            it.copy(
                city = value.trim()
            )
        }
    }

    fun updateIsPasswordVisible(value: Boolean) {
        _screenState.update { it.copy(isPasswordVisible = value) }
    }

    private fun updateIsLoadingState(isLoading: Boolean) {
        _signUpState.update {
            it.copy(
                isLoading = isLoading
            )
        }
    }

    private fun handleFieldError(exception: FieldErrorException) {
        Timber.d("DEBUG ====> ${exception.errorFields}")
        exception.errorFields.forEach { errorField ->
            if (errorField.first == NAME_FIELD) {
                _screenState.update {
                    it.copy(
                        errorNameMsg = errorField.second,
                        isNameError = true
                    )
                }
            }
            if (errorField.first == EMAIL_FIELD) {
                _screenState.update {
                    it.copy(
                        errorEmailMsg = errorField.second,
                        isEmailError = true
                    )
                }
            }
            if (errorField.first == PASSWORD_FIELD) {
                _screenState.update {
                    it.copy(
                        errorPasswordMsg = errorField.second,
                        isPasswordError = true
                    )
                }
            }
            if (errorField.first == PHONE_NUMBER_FIELD) {
                _screenState.update {
                    it.copy(
                        errorPhoneNumberMsg = errorField.second,
                        isPhoneNumberError = true
                    )
                }
            }
            if (errorField.first == ADDRESS_FIELD) {
                _screenState.update {
                    it.copy(
                        errorAddressMsg = errorField.second,
                        isAddressError = true
                    )
                }
            }
            if (errorField.first == HOUSE_NUMBER_FIELD) {
                _screenState.update {
                    it.copy(
                        errorHouseNumberMsg = errorField.second,
                        isHouseNumberError = true
                    )
                }
            }
            if (errorField.first == CITY_FIELD) {
                _screenState.update {
                    it.copy(
                        errorCityMsg = errorField.second,
                        isCityError = true
                    )
                }
            }
        }
    }
}