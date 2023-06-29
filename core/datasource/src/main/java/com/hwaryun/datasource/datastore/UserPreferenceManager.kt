package com.hwaryun.datasource.datastore

import com.hwaryun.network.model.response.AuthDto
import kotlinx.coroutines.flow.Flow

interface UserPreferenceManager {

    val readOnBoardingState: Flow<Boolean>

    val user: Flow<AuthDto.UserDto?>

    suspend fun saveOnBoardingState(completed: Boolean)

    suspend fun saveUserToken(token: String?)

    suspend fun saveUser(user: AuthDto.UserDto?)

    suspend fun clearUser()
}