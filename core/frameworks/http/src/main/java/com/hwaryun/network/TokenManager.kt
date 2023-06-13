package com.hwaryun.network

import kotlinx.coroutines.flow.Flow

interface TokenManager {
    val userToken: Flow<String>
}

