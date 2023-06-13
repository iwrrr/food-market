package com.hwaryun.network

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val tokenManager: TokenManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        val token = runBlocking {
            tokenManager.userToken.first()
        }
        request.addHeader("Authorization", "Bearer $token")
        return chain.proceed(request.build())
    }
}