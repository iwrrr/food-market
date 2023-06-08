package com.hwaryun.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hwaryun.common.FieldErrorException
import com.hwaryun.common.ext.suspendSubscribe
import com.hwaryun.domain.usecase.sign_in.SignInUseCase
import com.hwaryun.domain.utils.EMAIL_FIELD
import com.hwaryun.domain.utils.PASSWORD_FIELD
import com.hwaryun.signin.state.SignInScreenState
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

    private val _signInState = MutableStateFlow(SignInState())
    val signInState = _signInState.asStateFlow()

    private val _screenState = MutableStateFlow(SignInScreenState())
    val screenState = _screenState.asStateFlow()

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            signInUseCase.execute(SignInUseCase.Param(email, password)).collect { result ->
                result.suspendSubscribe(
                    doOnLoading = {
                        _signInState.emit(
                            SignInState(
                                isLoading = true
                            )
                        )
                    },
                    doOnSuccess = {
                        _signInState.emit(
                            SignInState(
                                signIn = result.value
                            )
                        )
                        updateIsLoadingState(isLoading = false)
                    },
                    doOnError = {
                        if (it.throwable is FieldErrorException) {
                            handleFieldError(it.throwable as FieldErrorException)
                        } else {
                            _signInState.emit(
                                SignInState(
                                    error = result.throwable?.message ?: "Unexpected error accrued"
                                )
                            )
                        }
                        updateIsLoadingState(isLoading = false)
                    }
                )
            }
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

    fun updateIsPasswordVisible(value: Boolean) {
        _screenState.update { it.copy(isPasswordVisible = value) }
    }

    private fun updateIsLoadingState(isLoading: Boolean) {
        _signInState.update {
            it.copy(
                isLoading = isLoading
            )
        }
    }

    private fun handleFieldError(exception: FieldErrorException) {
        exception.errorFields.forEach { errorField ->
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
        }
    }
}