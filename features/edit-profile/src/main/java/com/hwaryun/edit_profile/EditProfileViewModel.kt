package com.hwaryun.edit_profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hwaryun.common.ext.subscribe
import com.hwaryun.datasource.datastore.UserPreferenceManager
import com.hwaryun.domain.mapper.toUser
import com.hwaryun.domain.usecase.profile.UpdatePhotoUseCase
import com.hwaryun.domain.usecase.profile.UpdateProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val updatePhotoUseCase: UpdatePhotoUseCase,
    private val userPreferenceManager: UserPreferenceManager
) : ViewModel() {

    private val _state = MutableStateFlow(EditProfileState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            userPreferenceManager.user.collect { result ->
                if (result != null) {
                    _state.update { state ->
                        state.copy(
                            user = result.toUser(),
                            name = result.name.orEmpty(),
                            phoneNumber = result.phoneNumber.orEmpty(),
                            email = result.email.orEmpty(),
                        )
                    }
                }
            }
        }
    }

    fun changePhoto(photo: File?) {
        viewModelScope.launch {
            updatePhotoUseCase.invoke(photo).collect { result ->
                result.subscribe(
                    doOnLoading = {
                        _state.update { state ->
                            state.copy(isLoading = true)
                        }
                    },
                    doOnSuccess = {
                        _state.update { state ->
                            state.copy(
                                updatePhoto = it.value,
                                isLoading = false
                            )
                        }
                    },
                    doOnError = {
                        _state.update { state ->
                            state.copy(
                                isLoading = false,
                                error = result.throwable?.message ?: "Unexpected error occurred"
                            )
                        }
                    }
                )
            }
        }
    }

    fun updateProfile(
        name: String? = null,
        phoneNumber: String? = null,
        email: String? = null,
    ) {
        viewModelScope.launch {
            updateProfileUseCase.invoke(
                UpdateProfileUseCase.Param(
                    name = name,
                    email = email,
                    phoneNumber = phoneNumber
                )
            ).collect { result ->
                result.subscribe(
                    doOnLoading = {
                        _state.update { state ->
                            state.copy(isLoading = true)
                        }
                    },
                    doOnSuccess = {
                        _state.update { state ->
                            state.copy(
                                updateProfile = it.value,
                                isLoading = false
                            )
                        }
                    },
                    doOnError = {
                        _state.update { state ->
                            state.copy(
                                isLoading = false,
                                error = result.throwable?.message ?: "Unexpected error occurred"
                            )
                        }
                    }
                )
            }
        }
    }

    fun updatePassword(password: String? = null) {
        viewModelScope.launch {
            updateProfileUseCase.invoke(
                UpdateProfileUseCase.Param(password = password)
            ).collect { result ->
                result.subscribe(
                    doOnLoading = {
                        _state.update { state ->
                            state.copy(isLoading = true)
                        }
                    },
                    doOnSuccess = {
                        _state.update { state ->
                            state.copy(
                                updateProfile = it.value,
                                isLoading = false
                            )
                        }
                    },
                    doOnError = {
                        _state.update { state ->
                            state.copy(
                                isLoading = false,
                                error = result.throwable?.message ?: "Unexpected error occurred"
                            )
                        }
                    }
                )
            }
        }
    }

    fun updateNameState(value: String) {
        _state.update {
            it.copy(
                name = value,
                isNameError = false
            )
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

    fun updatePhoneNumberState(value: String) {
        _state.update {
            it.copy(
                phoneNumber = value.trim(),
                isPhoneNumberError = false
            )
        }
    }

    fun resetErrorState() {
        _state.update {
            it.copy(error = "")
        }
    }
}