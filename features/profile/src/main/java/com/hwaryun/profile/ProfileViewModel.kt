package com.hwaryun.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hwaryun.common.ext.subscribe
import com.hwaryun.datasource.datastore.UserPreferenceManager
import com.hwaryun.domain.mapper.toUser
import com.hwaryun.domain.usecase.auth.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val userPreferenceManager: UserPreferenceManager
) : ViewModel() {

    private val _profileState = MutableStateFlow(ProfileState())
    val profileState = _profileState.asStateFlow()

    init {
        viewModelScope.launch {
            userPreferenceManager.user.collect { result ->
                if (result != null) {
                    _profileState.update { state ->
                        state.copy(user = result.toUser())
                    }
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            logoutUseCase.invoke().collect { result ->
                result.subscribe(
                    doOnLoading = {
                        _profileState.update { state ->
                            state.copy(isLoading = true)
                        }
                    },
                    doOnSuccess = {
                        _profileState.update { state ->
                            state.copy(
                                logout = it.value,
                                isLoading = false
                            )
                        }
                    },
                    doOnError = {
                        _profileState.update { state ->
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

    fun resetErrorState() {
        _profileState.update {
            it.copy(error = "")
        }
    }
}