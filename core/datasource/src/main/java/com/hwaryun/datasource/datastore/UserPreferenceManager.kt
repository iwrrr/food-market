package com.hwaryun.datasource.datastore

import com.hwaryun.network.model.response.AuthDto
import kotlinx.coroutines.flow.Flow

interface UserPreferenceManager {
    val userToken: Flow<String>

    val user: Flow<AuthDto.UserDto>

    suspend fun saveUserToken(token: String?)

    suspend fun saveUser(user: AuthDto.UserDto)
}