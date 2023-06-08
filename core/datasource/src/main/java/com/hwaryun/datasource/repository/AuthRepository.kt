package com.hwaryun.datasource.repository

import com.hwaryun.common.result.NetworkClientResult
import com.hwaryun.network.model.response.AuthDto
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun signIn(
        email: String,
        password: String
    ): Flow<NetworkClientResult<AuthDto?>>

    suspend fun signUp(
        name: String,
        email: String,
        password: String,
        address: String,
        city: String,
        houseNumber: String,
        phoneNumber: String,
    ): Flow<NetworkClientResult<AuthDto?>>
}