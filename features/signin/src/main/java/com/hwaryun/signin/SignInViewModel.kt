package com.hwaryun.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hwaryun.common.FieldErrorException
import com.hwaryun.common.ext.suspendSubscribe
import com.hwaryun.domain.usecase.auth.SignInUseCase
import com.hwaryun.domain.utils.EMAIL_FIELD
import com.hwaryun.domain.utils.PASSWORD_FIELD
import com.hwaryun.signin.state.SignInUiState
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

    private val _signInUiState = MutableStateFlow(SignInUiState())
    val signInUiState = _signInUiState.asStateFlow()

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            signInUseCase.execute(SignInUseCase.Param(email, password)).collect { result ->
                result.suspendSubscribe(
                    doOnLoading = {
                        _signInUiState.update {
                            it.copy(
                                isLoading = true
                            )
                        }
                    },
                    doOnSuccess = {
                        _signInUiState.update {
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
                            _signInUiState.update { state ->
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
        _signInUiState.update {
            it.copy(
                email = value.trim(),
                isEmailError = false
            )
        }
    }

    fun updatePasswordState(value: String) {
        _signInUiState.update {
            it.copy(
                password = value.trim(),
                isPasswordError = false
            )
        }
    }

    fun updateIsPasswordVisible(value: Boolean) {
        _signInUiState.update { it.copy(isPasswordVisible = value) }
    }

    private fun handleFieldError(exception: FieldErrorException) {
        exception.errorFields.forEach { errorField ->
            if (errorField.first == EMAIL_FIELD) {
                _signInUiState.update {
                    it.copy(
                        errorEmailMsg = errorField.second,
                        isEmailError = true,
                        isLoading = false,
                    )
                }
            }
            if (errorField.first == PASSWORD_FIELD) {
                _signInUiState.update {
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