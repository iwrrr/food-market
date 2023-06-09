package com.hwaryun.foodmarket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hwaryun.datasource.datastore.UserPreferenceManager
import com.hwaryun.home.navigation.homeGraphRoute
import com.hwaryun.login.navigation.loginGraphRoute
import com.hwaryun.onboarding.navigation.onBoardingRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userPreferenceManager: UserPreferenceManager
) : ViewModel() {

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private val _startDestination: MutableStateFlow<String> = MutableStateFlow(loginGraphRoute)
    val startDestination = _startDestination.asStateFlow()

    init {
        getOnBoardingState()
    }

    private fun getOnBoardingState() {
        viewModelScope.launch {
            userPreferenceManager.readOnBoardingState.collect { completed ->
                if (completed) {
                    checkUserIsExist()
                } else {
                    _startDestination.value = onBoardingRoute
                }
                delay(300)
                _isLoading.value = false
            }
        }
    }

    private fun checkUserIsExist() {
        viewModelScope.launch {
            userPreferenceManager.user.collect { user ->
                if (user != null) {
                    _startDestination.value = homeGraphRoute
                } else {
                    _startDestination.value = loginGraphRoute
                }
            }
        }
    }
}