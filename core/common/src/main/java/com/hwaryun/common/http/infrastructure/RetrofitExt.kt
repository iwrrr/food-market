package com.hwaryun.common.http.infrastructure

import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.hwaryun.common.BaseResponse
import com.hwaryun.common.NetworkClientException
import com.hwaryun.common.UnexpectedValuesRepresentation
import com.hwaryun.common.result.NetworkClientResult
import okhttp3.ResponseBody
import retrofit2.HttpException
import timber.log.Timber

suspend fun <T> execute(block: suspend () -> T): NetworkClientResult<T> {
    return try {
        NetworkClientResult.Success(block.invoke())
    } catch (throwable: Throwable) {

        if (throwable is HttpException) {
            /**
             * Handle http status code can reference to
             * https://developer.mozilla.org/en-US/docs/Web/HTTP/Status#client_error_responses
             * */
            when (throwable.code()) {
                in 300..399 -> {
                    NetworkClientResult.Failure(NetworkClientException(getErrorMessageFromApi(throwable.response()?.errorBody())))
                }
                in 400..499 -> {
                    NetworkClientResult.Failure(NetworkClientException(getErrorMessageFromApi(throwable.response()?.errorBody())))
                }
                in 500..599 -> {
                    NetworkClientResult.Failure(NetworkClientException(getErrorMessageFromApi(throwable.response()?.errorBody())))
                }
                else -> {
                    NetworkClientResult.Failure(UnexpectedValuesRepresentation())
                }
            }
        } else {
            NetworkClientResult.Failure(throwable)
        }
    }
}

fun <T> getErrorMessageFromApi(response: T): String {
    val responseBody = response as ResponseBody
    return try {
        val gson = Gson()
        val body = gson.fromJson(responseBody.string(), BaseResponse::class.java)
        Timber.e("ERROR ====> ${body.meta?.message}")
        body.meta?.message ?: "Error Api"
    } catch (e: JsonParseException) {
        Timber.e(e, "ERROR ====> ${e.message}")
        "Error Api"
    }
}