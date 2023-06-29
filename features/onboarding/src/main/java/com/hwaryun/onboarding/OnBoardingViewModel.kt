package com.hwaryun.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hwaryun.datasource.datastore.UserPreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val userPreferenceManager: UserPreferenceManager
) : ViewModel() {

    fun saveOnBoardingState(completed: Boolean) {
        viewModelScope.launch {
            Timber.d("DEBUG ====> $completed")
            userPreferenceManager.saveOnBoardingState(completed)
        }
    }
}