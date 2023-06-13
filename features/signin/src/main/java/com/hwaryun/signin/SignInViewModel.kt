package com.hwaryun.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hwaryun.common.FieldErrorException
import com.hwaryun.common.ext.suspendSubscribe
import com.hwaryun.domain.usecase.sign_in.SignInUseCase
import com.hwaryun.domain.utils.EMAIL_FIELD
import com.hwaryun.domain.utils.PASSWORD_FIELD
import com.hwaryun.signin.state.SignInState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignInState())
    val uiState = _uiState.asStateFlow()

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            signInUseCase.execute(SignInUseCase.Param(email, password)).collect { result ->
                result.suspendSubscribe(
                    doOnLoading = {
                        _uiState.update {
                            it.copy(
                                isLoading = true
                            )
                        }
                    },
                    doOnSuccess = {
                        _uiState.update {
                            it.copy(
                                signIn = result.value,
                                isLoading = false
                            )
                        }
                    },
                    doOnError = {
                        if (it.throwable is FieldErrorException) {
                            handleFieldError(it.throwable as FieldErrorException)
                        } else {
                            _uiState.update { state ->
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

    fun updateEmailState(value: String) {
        _uiState.update {
            it.copy(
                email = value.trim(),
                isEmailError = false
            )
        }
    }

    fun updatePasswordState(value: String) {
        _uiState.update {
            it.copy(
                password = value.trim(),
                isPasswordError = false
            )
        }
    }

    fun updateIsPasswordVisible(value: Boolean) {
        _uiState.update { it.copy(isPasswordVisible = value) }
    }

    private fun handleFieldError(exception: FieldErrorException) {
        exception.errorFields.forEach { errorField ->
            if (errorField.first == EMAIL_FIELD) {
                _uiState.update {
                    it.copy(
                        errorEmailMsg = errorField.second,
                        isEmailError = true,
                        isLoading = false,
                    )
                }
            }
            if (errorField.first == PASSWORD_FIELD) {
                _uiState.update {
                    it.copy(
                        errorPasswordMsg = errorField.second,
                        isPasswordError = true,
                        isLoading = false,
                    )
                }
            }
        }
    }
}