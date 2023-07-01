package com.hwaryun.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hwaryun.common.FieldErrorException
import com.hwaryun.common.ext.suspendSubscribe
import com.hwaryun.datasource.util.NetworkMonitor
import com.hwaryun.domain.usecase.auth.LoginUseCase
import com.hwaryun.domain.utils.EMAIL_FIELD
import com.hwaryun.domain.utils.PASSWORD_FIELD
import com.hwaryun.login.state.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    networkMonitor: NetworkMonitor
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    val isOffline = networkMonitor.isOnline
        .map(Boolean::not)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false
        )

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            loginUseCase.invoke(LoginUseCase.Param(email, password)).collect { result ->
                result.suspendSubscribe(
                    doOnLoading = {
                        _state.update {
                            it.copy(
                                isLoading = true
                            )
                        }
                    },
                    doOnSuccess = {
                        _state.update {
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
                            _state.update { state ->
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
        _state.update {
            it.copy(
                email = value.trim(),
                isEmailError = false
            )
        }
    }

    fun updatePasswordState(value: String) {
        _state.update {
            it.copy(
                password = value.trim(),
                isPasswordError = false
            )
        }
    }

    fun updateIsPasswordVisible(value: Boolean) {
        _state.update { it.copy(isPasswordVisible = value) }
    }

    fun resetErrorState() {
        _state.update {
            it.copy(error = "")
        }
    }

    private fun handleFieldError(exception: FieldErrorException) {
        exception.errorFields.forEach { errorField ->
            if (errorField.first == EMAIL_FIELD) {
                _state.update {
                    it.copy(
                        errorEmailMsg = errorField.second,
                        isEmailError = true,
                        isLoading = false,
                    )
                }
            }
            if (errorField.first == PASSWORD_FIELD) {
                _state.update {
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